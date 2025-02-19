package com.gsb.gsbecommercebackend.service.orders;

import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.dao.deliveryAddress.DeliveryAddressDAO;
import com.gsb.gsbecommercebackend.dao.orders.OrderDAO;
import com.gsb.gsbecommercebackend.dao.orderedItem.OrderedItemDAO;
import com.gsb.gsbecommercebackend.dao.products.ProductDAO;
import com.gsb.gsbecommercebackend.dao.users.UsersDAO;
import com.gsb.gsbecommercebackend.dto.OrderSummaryDTO;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import com.gsb.gsbecommercebackend.model.orderedItemClass.OrderedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Annotation Service
@Service
public class OrderService {

    // Injection du DAO dans notre Service
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderedItemDAO orderedItemDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private DeliveryAddressDAO deliveryAddressDAO;

    @Transactional
    public int createOrderWithItems(Order order, List<OrderedItem> orderedItems) {
        // Insère la commande globale
        int orderId = orderDAO.createOrder(order);
        System.out.println("Order ID généré (service) : " + orderId);

        if (orderId == -1) {
            throw new RuntimeException("Erreur : orderId invalide !");
        }

        // Pour chaque item dans des items_ordered on boucle et applique la logique
        for (OrderedItem item : orderedItems) {

            int currentStock = productDAO.getCurrentStockById(item.getProductId());
            System.out.println("Le stock actuel est de : " + currentStock );
            if (currentStock < item.getOrderedItemsQuantity()){
                throw new RuntimeException("Stock Insuffisant pour le produit à l'id" + item.getProductId());
            }

            productDAO.decreaseStock(item.getProductId(), item.getOrderedItemsQuantity());

            item.setOrderId(orderId);
            System.out.println("Tentative d'ajout du produit : " + item.getProductId());
            orderedItemDAO.addOrderedItem(item);
            System.out.println("Produit inséré : " + item.getProductId());
        }

        return orderId;
    }

    // Le transactionnal est un des principes du concept ACID (atomicité / cohérence (consistency) / integrité / durabilité
    @Transactional
    public Map<String, Object> getOrderDetailsByOrderId(int orderId) {
        // Récupérer la commande
        Order order = orderDAO.getOrderDetailsByOrderId(orderId);
        if (order == null) {
            throw new RuntimeException("Commande non trouvée pour ID : " + orderId);
        }

        // On récupère l'ensemble des items commandés en fonction de l'id de commande
        List<OrderedItem> orderedItems = orderedItemDAO.getOrderedItemsByOrderId(orderId);

        // On récpère le nom - prénom de l'utilisateur via la méthode getUserNameByUser
        String userName = usersDAO.getUserNameByUserId(order.getUserId());

        // On récupère l'adresse complète de livraison
        String deliveryAddressName = deliveryAddressDAO.getDeliveryAddressNameById(order.getDeliveryAddressId());

        // Ajouter les détails des produits pour chaque article commandé
        List<Map<String, Object>> itemsDetails = new ArrayList<>();
        for (OrderedItem item : orderedItems) {
            Map<String, Object> productDetails = new HashMap<>();
            String productName = productDAO.getProductNameById(item.getProductId());
            productDetails.put("productName", productName);
            productDetails.put("quantity", item.getOrderedItemsQuantity());
            productDetails.put("unitPrice", item.getOrderedItemsUnitPrice());
            itemsDetails.add(productDetails);
        }

        // Construire une réponse claire
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.getOrderId());
        response.put("userName", userName);
        response.put("orderStatus", order.getOrderStatus());
        response.put("orderTotalPrice", order.getOrderTotalPrice());
        response.put("orderCreatedAt", order.getOrderCreatedAt());
        response.put("deliveryAddressName", deliveryAddressName);
        response.put("items", itemsDetails);

        return response;
    }

    /* Gérer la liste des commandes pour utilisateur */

    public List<Map<String, Object>> getDetailedOrdersByUserId (Integer userId){

        try {
            return orderDAO.getDetailedOrdersByUserId(userId);
        } catch (DaoException e){
            System.err.println("Erreur dans le service OrderService : " + e.getMessage());
            throw e;
        }
    }

    public List<OrderSummaryDTO> getOrdersByRegion(int deliveryAddressId) {
        return orderDAO.getOrdersByRegion(deliveryAddressId);
    }


    public String updateOrderStatus(int orderId, String newStatus) {
        int updatedRows = orderDAO.updateOrderStatus(orderId, newStatus);
        if (updatedRows > 0) {
            return "Statut mis à jour avec succès";
        } else {
            return "Aucune commande trouvée avec cet ID";
        }
    }

}
