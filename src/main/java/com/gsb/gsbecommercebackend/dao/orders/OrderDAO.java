package com.gsb.gsbecommercebackend.dao.orders;

import com.gsb.gsbecommercebackend.customExceptions.orders.OrderCreationException;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

        System.out.println("Création de la commande avec : " + order);


        System.out.println("Insertion de la commande avec les valeurs : " +
                order.getUserId() + ", " + order.getOrderStatus() + ", " +
                order.getOrderTotalPrice() + ", " + order.getDeliveryAddressId() + ", " +
                order.getOrderCreatedAt());




        jdbcTemplate.update(
                sql,
                order.getUserId(),
                order.getOrderStatus(),
                order.getOrderTotalPrice(),
                order.getDeliveryAddressId()
        );



        // Récupérer l'ID généré
        Integer generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        System.out.println("Order ID généré (DAO) : " + generatedId);
        if (generatedId == null || generatedId == 0) {
            throw new OrderCreationException("Échec de la récupération de l'ID généré !");
        }


        return generatedId; // Retourne -1 en cas d'erreur
    }


    public Order getOrderDetailsByOrderId(int orderId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_ID + "= ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), orderId);

    }

//    public List<Order> getOrdersByUserId(int userId) {
//        String sql = "SELECT * FROM " + ORDERS_TABLE +
//                " WHERE " + USER_ID + " = ? ORDER BY " + ORDER_CREATED_AT + " DESC";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class), userId);
//    }


}

