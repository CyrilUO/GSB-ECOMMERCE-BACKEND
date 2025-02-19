package com.gsb.gsbecommercebackend.controller.orders;


import com.gsb.gsbecommercebackend.constant.OrdersConstant;
import com.gsb.gsbecommercebackend.dto.OrderSummaryDTO;
import com.gsb.gsbecommercebackend.model.usersClass.CustomUserDetails;
import com.gsb.gsbecommercebackend.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import com.gsb.gsbecommercebackend.model.orderedItemClass.OrderedItem;
import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.constant.AppConstants.OrderStatusEnum;


import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderStatusEnum.*;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderCRUDController {

    /*Injecte automatiquement une dépendance (instance de la classe OrderService) dans une
     * autre classe géré par le contexte Spring (OrderController)
     * Spring detecte l'annotation et va chercher dans le conteneur une instance de la classe ou un Bean pour l'injecter*/
    @Autowired
    private OrderService orderService;


    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailsByOrderId(@PathVariable int orderId) {
        try {
            Map<String, Object> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> payload) {
        try {
            // Récupérer l'ID utilisateur depuis CustomUserDetails
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            int userId = userDetails.getUserId();

            System.out.println("ID utilisateur récupéré pour créer la commande : " + userId);


            // Récupérer les valeurs depuis le payload
            if (!payload.containsKey("deliveryAddressId") || payload.get("deliveryAddressId") == null) {
                throw new IllegalArgumentException("deliveryAddressId manquant dans le payload !");
            }
            int deliveryAddressId = ((Number) payload.get("deliveryAddressId")).intValue();

            if (!payload.containsKey("orderTotalPrice") || payload.get("orderTotalPrice") == null) {
                throw new IllegalArgumentException("orderTotalPrice manquant dans le payload !");
            }

            float orderTotalPrice = ((Number) payload.get("orderTotalPrice")).floatValue();

            System.out.println("Payload reçu : " + payload);

            // Récupérer les items
            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
            if (items == null || items.isEmpty()) {
                throw new IllegalArgumentException("La liste des items est vide ou manquante !");
            }

            System.out.println("Items : " + items);


            // Construire l'objet Order
            Order order = new Order();
            order.setUserId(userId);
            order.setDeliveryAddressId(deliveryAddressId);
            order.setOrderTotalPrice(orderTotalPrice);
            order.setOrderStatus(ORDER_AWAITING);


            if (order.getOrderStatus() == null || order.getOrderStatus().isEmpty()) {
                throw new IllegalArgumentException("Status pas ajouté en bdd");
            }

            System.out.println("Status de la commande : " + order.getOrderStatus());

            // Traiter les produits commandés
            List<OrderedItem> orderedItems = items.stream().map(item -> {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setProductId(((Number) item.get("productId")).intValue());
                orderedItem.setOrderedItemsQuantity(((Number) item.get("orderedItemsQuantity")).intValue());
                orderedItem.setOrderedItemsUnitPrice(((Number) item.get("orderedItemsUnitPrice")).floatValue());
                return orderedItem;
            }).toList();

            int orderId = orderService.createOrderWithItems(order, orderedItems);

            Map<String, Object> response = Map.of("orderId", orderId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erreur lors de la création de la commande"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable int userId) {
        try {
            System.out.println("Fetching orders for user ID: " + userId);
            List<Map<String, Object>> orders = orderService.getDetailedOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (DaoException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des commandes : " + e.getMessage());
        }
    }

    @GetMapping("/region/{deliveryAddressId}")
    public ResponseEntity<List<OrderSummaryDTO>> getOrdersByRegion(@PathVariable int deliveryAddressId) {
        List<OrderSummaryDTO> orders = orderService.getOrdersByRegion(deliveryAddressId);

        if (orders.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(orders);
    }


    @PatchMapping("/update-status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestBody Map<String, String> updates) {
        String newStatus = updates.get("orderStatus");

        System.out.println("🟢 JSON reçu : " + updates);


        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'orderStatus' est requis.");
        }

        String newStatusToEnum = mapStatus(newStatus);
        if (newStatusToEnum == null) {
            return ResponseEntity.badRequest().body("Statut de commande non valide");
        }

        String responseMessage = orderService.updateOrderStatus(orderId, newStatusToEnum);
        return ResponseEntity.ok(responseMessage);
    }

    private String mapStatus(String shippingStatus) {
        return switch (shippingStatus) {
            case "En attente" -> ORDER_AWAITING;
            case "Expédié" -> ORDER_SENT;
            case "Livré" -> ORDER_SHIPPED;
            default -> null;
        };
    }
}


