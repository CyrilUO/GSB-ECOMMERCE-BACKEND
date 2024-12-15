package com.gsb.gsbecommercebackend.service;

import com.gsb.gsbecommercebackend.dao.OrderDAO;
import com.gsb.gsbecommercebackend.dao.OrderedItemDAO;
import com.gsb.gsbecommercebackend.model.Order;
import com.gsb.gsbecommercebackend.model.OrderedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderedItemDAO orderedItemDAO;

    @Transactional
    public int createOrderWithItems(Order order, List<OrderedItem> orderedItems) {
        // Insère la commande globale
        int orderId = orderDAO.createOrder(order);
        System.out.println("Order ID généré : " + orderId);

        if (orderId == -1) {
            throw new RuntimeException("Erreur : orderId invalide !");
        }

        // Insère chaque produit associé
        for (OrderedItem item : orderedItems) {
            item.setOrderId(orderId);
            System.out.println("Tentative d'ajout du produit : " + item.getProductId());
            orderedItemDAO.addOrderedItem(item);
            System.out.println("Produit inséré : " + item.getProductId());
        }

        return orderId;
    }

}
