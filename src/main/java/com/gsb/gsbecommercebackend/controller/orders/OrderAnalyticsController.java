package com.gsb.gsbecommercebackend.controller.orders;

import com.gsb.gsbecommercebackend.service.orders.OrderAnalyticsService;
import com.gsb.gsbecommercebackend.service.orders.OrderService;

import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class OrderAnalyticsController {

    @Autowired
    private OrderAnalyticsService orderAnalyticsService;

    @GetMapping("/orders/stats/daily-orders")
    public ResponseEntity<List<Map<String, Object>>> getDailyOrderAmount() {
        try {
            // Appelle le service pour récupérer les données
            List<Map<String, Object>> dailyCounts = orderAnalyticsService.getDailyOrderAmount();

            // Vérifie si les données sont vides
            if (dailyCounts == null || dailyCounts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Retourne les résultats avec un statut HTTP 200 (OK)
            return ResponseEntity.ok(dailyCounts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
