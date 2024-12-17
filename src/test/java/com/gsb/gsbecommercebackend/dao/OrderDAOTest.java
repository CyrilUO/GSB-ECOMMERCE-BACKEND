package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional // Permet de rollback automatiquement après chaque test
@Rollback // Assure qu'il n'y a pas d'effets persistants
public class OrderDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderDAO orderDAO;

    @Test
    public void testCreateOrder() {
        // Insérer un utilisateur valide dans la table users
        jdbcTemplate.update("INSERT INTO users (user_name, user_surname, user_email, user_password, user_role) VALUES (?, ?, ?, ?, ?)",
                "John", "Doe", "john.doe@example.com", "password123", "USER");

        // Insérer une adresse de livraison
        jdbcTemplate.update("INSERT INTO delivery_address (delivery_address_city, delivery_address_street, delivery_address_zip_code, delivery_address_country) VALUES (?, ?, ?, ?)",
                "Paris", "123 Rue de Paris", 75000, "France");

        // Préparer l'objet Order
        Order order = new Order();
        order.setUserId(1); // ID de l'utilisateur inséré précédemment
        order.setDeliveryAddressId(1); // ID de l'adresse insérée précédemment
        order.setOrderStatus("En attente");
        order.setOrderTotalPrice(100.00f);

        // Tester l'insertion
        int generatedId = orderDAO.createOrder(order);
        assertTrue(generatedId > 0);
    }



    @Test
    public void testGetOrderById() {
        // Arrange : Insérer une commande directement avec JdbcTemplate
        jdbcTemplate.update(
                "INSERT INTO orders (user_id, order_status, order_total_price, delivery_address_id) VALUES (?, ?, ?, ?)",
                1, "En attente", 100.0F, 2
        );

        // Act : Récupérer la commande par ID
        Order order = orderDAO.getOrderById(1);

        // Assert : Vérifier les champs de la commande
        assertThat(order).isNotNull();
        assertThat(order.getUserId()).isEqualTo(1);
        assertThat(order.getOrderStatus()).isEqualTo("En attente");
        assertThat(order.getOrderTotalPrice()).isEqualTo(100.0F);
    }
}
