package com.gsb.gsbecommercebackend.dao;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderedItemsDataSource.*;

import com.gsb.gsbecommercebackend.model.OrderedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderedItemDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addOrderedItem(OrderedItem item) {
        try {
            String sql = "INSERT INTO " + ORDERED_ITEMS_TABLE + " (" +
                    PRODUCT_ID + ", " +
                    ORDERED_ITEMS_QUANTITY + ", " +
                    ORDERED_ITEMS_UNIT_PRICE + ", " +
                    ORDER_ID + ") VALUES (?, ?, ?, ?)";


            System.out.println("Requête SQL : " + sql);
            System.out.println("Paramètres : productId=" + item.getProductId() +
                    ", quantity=" + item.getOrderedItemsQuantity() +
                    ", unitPrice=" + item.getOrderedItemsUnitPrice() +
                    ", orderId=" + item.getOrderId());

            jdbcTemplate.update(sql,
                    item.getProductId(),
                    item.getOrderedItemsQuantity(),
                    item.getOrderedItemsUnitPrice(),
                    item.getOrderId()
            );
            System.out.println("Tentative d'ajout dans ordered_items : orderId=" + item.getOrderId() +
                    ", productId=" + item.getProductId());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion dans ordered_items : " + e.getMessage());
        }
    }


    public List<OrderedItem> getOrderedItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM " + ORDERED_ITEMS_TABLE + " WHERE " + ORDER_ID + " = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderedItem.class), orderId);
    }


}
