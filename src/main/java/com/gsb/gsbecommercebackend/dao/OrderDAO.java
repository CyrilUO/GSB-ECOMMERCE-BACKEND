package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.customExceptions.orders.OrderCreationException;
import com.gsb.gsbecommercebackend.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.*;


@Repository
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer createOrder(Order order) {
        String sql = "INSERT INTO "
                + ORDERS_TABLE + " ("
                + USER_ID + ", "
                + ORDER_STATUS + ", "
                + ORDER_TOTAL_PRICE + ", "
                + DELIVERY_ADDRESS_ID
                + ") VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                order.getUserId(),
                order.getOrderStatus(),
                order.getOrderTotalPrice(),
                order.getDeliveryAddressId()
        );



        // Récupérer l'ID généré
        Integer generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        System.out.println("Order ID généré : " + generatedId);
        if (generatedId == null || generatedId == 0) {
            throw new OrderCreationException("Échec de la récupération de l'ID généré !");
        }


        return generatedId; // Retourne -1 en cas d'erreur
    }


    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_ID + "= ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), orderId);

    }
}

