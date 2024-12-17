package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.model.OrderedItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class OrderedItemDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderedItemDAO orderedItemDAO;

    @Test
    public void testAddOrderedItem() {
        // Arrange : Insérer une commande nécessaire en amont
        jdbcTemplate.update(
                "INSERT INTO orders (user_id, order_status, order_total_price, delivery_address_id) VALUES (?, ?, ?, ?)",
                1, "En attente", 100.0F, 2
        );

        // Créer un OrderedItem
        OrderedItem item = new OrderedItem();
        item.setProductId(1);
        item.setOrderedItemsQuantity(3);
        item.setOrderedItemsUnitPrice(25.0F);
        item.setOrderId(1);

        // Act : Ajouter l'item
        orderedItemDAO.addOrderedItem(item);

        // Assert : Vérifier que l'item a bien été inséré
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ordered_items WHERE order_id = ? AND product_id = ?", Integer.class, 1, 1
        );
        assertThat(count).isEqualTo(1);
    }
}

