package com.gsb.gsbecommercebackend.controller;


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

            // Récupérer l'ID utilisateur connecté via le token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            int userId = Integer.parseInt(userDetails.getUsername()); // Récupère l'ID utilisateur

            int deliveryAddressId = ((Number) payload.get(DELIVERY_ADDRESS_ID)).intValue();
            float orderTotalPrice = ((Number) payload.get(ORDER_TOTAL_PRICE)).floatValue();

            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");

            System.out.println("Payload reçu : " + payload);

            Order order = new Order();
            order.setUserId(userId);
            order.setDeliveryAddressId(deliveryAddressId);
            order.setOrderTotalPrice(orderTotalPrice);
            order.setOrderStatus(ORDER_AWAITING);

            List<OrderedItem> orderedItems = items.stream().map(item -> {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setProductId(((Number) item.get(PRODUCT_ID)).intValue());
                orderedItem.setOrderedItemsQuantity(((Number) item.get(ORDERED_ITEMS_QUANTITY)).intValue());
                orderedItem.setOrderedItemsUnitPrice(((Number) item.get(ORDERED_ITEMS_UNIT_PRICE)).floatValue());

                return orderedItem;
            }).toList();

            int orderId = orderService.createOrderWithItems(order, orderedItems);
            return ResponseEntity.ok("Commande créée avec succès. L'id est : " + orderId);


        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète dans les logs

            return ResponseEntity.status(500).body("Erreur lors de la créa de commande");
        }
    }
}


