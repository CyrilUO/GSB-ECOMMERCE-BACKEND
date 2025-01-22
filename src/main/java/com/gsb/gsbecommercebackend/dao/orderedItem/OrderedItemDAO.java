package com.gsb.gsbecommercebackend.dao.orderedItem;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderedItemsDataSource.*;

import com.gsb.gsbecommercebackend.model.orderedItemClass.OrderedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderedItemDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderedItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addOrderedItem(OrderedItem item) {
        try {
            String sql = "INSERT INTO " + ORDERED_ITEMS_TABLE + " (" +
                    PRODUCT_ID + ", " +
                    ORDERED_ITEMS_QUANTITY + ", " +
                    ORDERED_ITEMS_UNIT_PRICE + ", " +
                    ORDER_ID + ") VALUES (?, ?, ?, ?)";


            System.out.println("Requête SQL : " + sql);


            jdbcTemplate.update(sql,
                    item.getProductId(),
                    item.getOrderedItemsQuantity(),
                    item.getOrderedItemsUnitPrice(),
                    item.getOrderId()
            );

            System.out.println("Ajout d'un article : " + item);

            System.out.println("Insertion dans ordered_items : " +
                    "orderId=" + item.getOrderId() +
                    ", productId=" + item.getProductId() +
                    ", itemQuantity=" + item.getOrderedItemsQuantity() +
                    ", itemUnitPrice=" + item.getOrderedItemsUnitPrice());

        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion dans ordered_items : " + e.getMessage());
            throw new RuntimeException("Insertion échouée dnas ordered_items", e);
        }
    }


    public List<OrderedItem> getOrderedItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM " + ORDERED_ITEMS_TABLE + " WHERE " + ORDER_ID + " = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderedItem.class), orderId);
    }


}
