package com.gsb.gsbecommercebackend.dao.orders;
import static com.gsb.gsbecommercebackend.constant.OrdersConstant.OrderSummary.*;



import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.PanelUI;
import javax.swing.text.View;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.*;

@Repository
public class OrderAnalyticsDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderAnalyticsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getDailyOrderAmount() {
        String sql = "SELECT DATE(" + ORDER_CREATED_AT + ") AS orderCreatedAt, COUNT(*) AS orderCount " +
                "FROM " + ORDERS_TABLE + " " +
                "GROUP BY DATE(" + ORDER_CREATED_AT + ") " +
                "ORDER BY DATE(" + ORDER_CREATED_AT + ") ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> row = new HashMap<>();
            row.put("orderCreatedAt", rs.getDate("orderCreatedAt")); // Corrigé
            row.put("orderCount", rs.getInt("orderCount"));               // Corrigé
            return row;
        });
    }

//    public List<Map<String, Object>> getOrdersByArea(int delivery_address_id){
//
//        String sql = "SELECT * FROM" + VIEW_ORDER_SUMMARY
//    }
}