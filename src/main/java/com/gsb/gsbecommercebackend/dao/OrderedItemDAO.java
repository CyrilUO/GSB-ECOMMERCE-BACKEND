package com.gsb.gsbecommercebackend.dao;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderedItemsDataSource.*;
import com.gsb.gsbecommercebackend.model.OrderedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class OrderedItemDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addOrderedItem(OrderedItem item) {
        String sql = "INSERT INTO "
                + ORDERED_ITEMS_TABLE
                + " ("
                + PRODUCT_ID + ", "
                + ORDERED_ITEMS_QUANTITY + ", "
                + ORDERED_ITEMS_UNIT_PRICE + ", "
                + ORDER_ID
                + ") VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                item.getProductId(),
                item.getOrderedItemsQuantity(),
                item.getOrderedItemsUnitPrice(),
                item.getOrderId()
        );
    }
}
