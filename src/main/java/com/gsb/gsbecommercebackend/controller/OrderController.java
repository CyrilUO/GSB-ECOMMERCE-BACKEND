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
            Object deliveryAddressIdObj = payload.get(DELIVERY_ADDRESS_ID);
            if (deliveryAddressIdObj == null) {
                throw new IllegalArgumentException("delivery_address_id manquant dans le payload !");
            }
            int deliveryAddressId;

            if (payload.containsKey("deliveryAddressId")) { // Version camelCase
                deliveryAddressId = ((Number) payload.get("deliveryAddressId")).intValue();
            } else if (payload.containsKey(DELIVERY_ADDRESS_ID)) { // Version snake_case
                deliveryAddressId = ((Number) payload.get(DELIVERY_ADDRESS_ID)).intValue();
            } else {
                throw new IllegalArgumentException("delivery_address_id manquant dans le payload !");
            }

            float orderTotalPrice = ((Number) payload.get(ORDER_TOTAL_PRICE)).floatValue();

            System.out.println("Payload reçu : " + payload);
            System.out.println("Clé DELIVERY_ADDRESS_ID : " + DELIVERY_ADDRESS_ID);
            System.out.println("Valeur associée : " + payload.get(DELIVERY_ADDRESS_ID));


            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");

            Order order = new Order();
            order.setUserId(userId);
            order.setDeliveryAddressId(deliveryAddressId); // Correction ici
            order.setOrderTotalPrice(orderTotalPrice);
            order.setOrderStatus(ORDER_AWAITING);

            // Traiter les produits commandés
            List<OrderedItem> orderedItems = items.stream().map(item -> {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setProductId(((Number) item.get(PRODUCT_ID)).intValue());
                orderedItem.setOrderedItemsQuantity(((Number) item.get(ORDERED_ITEMS_QUANTITY)).intValue());
                orderedItem.setOrderedItemsUnitPrice(((Number) item.get(ORDERED_ITEMS_UNIT_PRICE)).floatValue());
                return orderedItem;
            }).toList();

            int orderId = orderService.createOrderWithItems(order, orderedItems);
            return ResponseEntity.ok("Commande créée avec succès. L'ID de la commande est : " + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la création de la commande");
        }
    }

}


