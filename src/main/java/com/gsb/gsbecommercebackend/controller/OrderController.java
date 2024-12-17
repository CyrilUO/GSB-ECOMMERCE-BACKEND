package com.gsb.gsbecommercebackend.controller;


import com.gsb.gsbecommercebackend.model.CustomUserDetails;
import com.gsb.gsbecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.gsb.gsbecommercebackend.model.Order;
import com.gsb.gsbecommercebackend.model.OrderedItem;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.*;
import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderStatusEnum.*;
import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderedItemsDataSource.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

    /*Injecte automatiquement une dépendance (instance de la classe OrderService) dans une
    * autre classe géré par le contexte Spring (OrderController)
    * Spring detecte l'annotation et va chercher dans le conteneur une instance de la classe ou un Bean pour l'injecter*/
    @Autowired
    private OrderService orderService;

    @GetMapping("orders/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable int orderId) {
        try {
            Map<String, Object> orderDetails = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    @PostMapping("orders/create-order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> payload) {
        try {
            // Récupérer l'ID utilisateur depuis CustomUserDetails
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            int userId = userDetails.getUserId();

            System.out.println("ID utilisateur récupéré : " + userId);

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

            // Traiter les produits commandés
            List<OrderedItem> orderedItems = items.stream().map(item -> {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setProductId(((Number) item.get("productId")).intValue());
                orderedItem.setOrderedItemsQuantity(((Number) item.get("orderedItemsQuantity")).intValue());
                orderedItem.setOrderedItemsUnitPrice(((Number) item.get("orderedItemsUnitPrice")).floatValue());
                return orderedItem;
            }).toList();

            // Sauvegarder la commande
            int orderId = orderService.createOrderWithItems(order, orderedItems);
            return ResponseEntity.ok("L'ID de la commande est : " + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la création de la commande");
        }
    }


}


