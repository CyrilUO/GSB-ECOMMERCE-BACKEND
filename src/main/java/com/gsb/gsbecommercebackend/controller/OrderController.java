package com.gsb.gsbecommercebackend.controller;


import com.gsb.gsbecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private OrderService Orderservice;

    @PostMapping("orders/create-order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> payload) {
        try {

            // on récup^ère les données de la commande

            int userId = ((Number) payload.get(USER_ID)).intValue();
            int deliveryAddressId = ((Number) payload.get(DELIVERY_ADDRESS_ID)).intValue();
            float orderTotalPrice = ((Number) payload.get(ORDER_TOTAL_PRICE)).floatValue();

            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");

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

            int orderId = Orderservice.createOrderWithItems(order, orderedItems);
            return ResponseEntity.ok("Commande créée avec succès. L'id est : " + orderId);


        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète dans les logs

            return ResponseEntity.status(500).body("Erreur lors de la créa de commande");
        }
    }
}


