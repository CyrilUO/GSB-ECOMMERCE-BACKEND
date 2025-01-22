package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.dao.orders.OrderDAO;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
public class OrderDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderDAO orderDAO;

    @BeforeEach
    public void setupDatabase() {
        // Supprimer les données existantes pour éviter les conflits
        jdbcTemplate.execute("DELETE FROM orders");
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM delivery_address");

        // Insérer un utilisateur fictif
        jdbcTemplate.update("INSERT INTO users (user_name, user_surname, user_email, user_password, user_role) " +
                "VALUES (?, ?, ?, ?, ?)", "John", "Doe", "john.doe@example.com", "password123", "USER");

        // Insérer une adresse fictive
        jdbcTemplate.update("INSERT INTO delivery_address (delivery_address_city, delivery_address_street, " +
                        "delivery_address_zip_code, delivery_address_country) VALUES (?, ?, ?, ?)",
                "Paris", "123 Rue de Paris", 75000, "France");
    }

    @Test
    public void testCreateOrder() {
        // Préparer l'objet Order
        Order order = new Order();
        order.setUserId(1); // ID utilisateur fictif inséré
        order.setDeliveryAddressId(1); // ID adresse fictive insérée
        order.setOrderStatus("En attente");
        order.setOrderTotalPrice(150.00f);

        // Tester l'insertion
        int generatedId = orderDAO.createOrder(order);
        assertTrue(generatedId > 0, "L'ID généré doit être supérieur à 0");
    }

//    @Test
//    public void testGetOrderById() {
//        // Insérer une commande directement
//        jdbcTemplate.update(
//                "INSERT INTO orders (user_id, order_status, order_total_price, delivery_address_id) VALUES (?, ?, ?, ?)",
//                1, "En attente", 150.00F, 1
//        );
//
//        // Récupérer la commande
//        Order order = orderDAO.getOrderById(1);
//
//        // Assertions
//        assertThat(order).isNotNull();
//        assertThat(order.getUserId()).isEqualTo(1);
//        assertThat(order.getOrderStatus()).isEqualTo("En attente");
//        assertThat(order.getOrderTotalPrice()).isEqualTo(150.00F);
//    }
}
