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
        jdbcTemplate.execute("DELETE FROM orders");
        jdbcTemplate.execute("DELETE FROM delivery_address");
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM roles");

        jdbcTemplate.update("INSERT INTO roles (role_id, role_name) VALUES (1, 'admin')");

        jdbcTemplate.update("INSERT INTO users (user_id, user_name, user_surname, user_email, user_password, role_id) " +
                "VALUES (1, ?, ?, ?, ?, ?)", "John", "Doe", "john.doe@example.com", "password123", 1);

        jdbcTemplate.update("INSERT INTO delivery_address (delivery_address_id, delivery_address_city, delivery_address_street, " +
                        "delivery_address_zip_code, delivery_address_country) VALUES (1, ?, ?, ?, ?)",
                "Paris", "123 Rue de Paris", 75000, "France");
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setUserId(1);
        order.setDeliveryAddressId(1);
        order.setOrderStatus("En attente");
        order.setOrderTotalPrice(150.00f);

        int generatedId = orderDAO.createOrder(order);
        assertTrue(generatedId > 0, "L'ID généré doit être supérieur à 0");
    }
}

