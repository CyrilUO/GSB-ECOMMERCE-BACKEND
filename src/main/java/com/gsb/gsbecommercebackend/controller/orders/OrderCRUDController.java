package com.gsb.gsbecommercebackend.controller.orders;


import com.gsb.gsbecommercebackend.model.usersClass.CustomUserDetails;
import com.gsb.gsbecommercebackend.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import com.gsb.gsbecommercebackend.model.orderedItemClass.OrderedItem;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderStatusEnum.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderCRUDController {

    /*Injecte automatiquement une dépendance (instance de la classe OrderService) dans une
     * autre classe géré par le contexte Spring (OrderController)
     * Spring detecte l'annotation et va chercher dans le conteneur une instance de la classe ou un Bean pour l'injecter*/
    @Autowired
    private OrderService orderService;



    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailsByOrderId(@PathVariable int orderId) {
        try {
            Map<String, Object> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PostMapping("orders/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> payload) {
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

//            int test = Integer.parseInt(String.valueOf(order.getOrderId()));
//
////
////            if(test.){
////                throw new IllegalArgumentException("L'utilisateur est nul");
////            }

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

//    @GetMapping("/orders/order-list/${userId}")
//    public ResponseEntity<List<Map<String, Object>>> getOrdersRecapByUserId() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            int userId = userDetails.getUserId();
//
//            List<Map<String, Object>> orders = orderService.getOrdersByUserId(userId);
//            return ResponseEntity.ok(orders);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body(null);
//        }
//    }



}


