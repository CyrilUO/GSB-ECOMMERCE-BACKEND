package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.constant.AppConstants;
import com.gsb.gsbecommercebackend.model.Product;
import com.gsb.gsbecommercebackend.model.builder.ProductBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gsb.gsbecommercebackend.constant.AppConstants.ProductDataSource.*;

/* Création du productDAO à l'initialisation du projet (Spring-Containeur)*/
@Repository
public class ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM " + PRODUCT_TABLE;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProductBuilder builder = new ProductBuilder();

            return builder
                    .withId(rs.getInt(PRODUCT_ID))
                    .withName(rs.getString(PRODUCT_NAME))
                    .withDescription(rs.getString(PRODUCT_DESCRIPTION))
                    .withPrice(rs.getFloat(PRODUCT_PRICE))
                    .withStock(rs.getInt(PRODUCT_STOCK))
                    .build();
        });
    }

    public Product addProduct(Product product){
        String sql = "INSERT INTO " + PRODUCT_TABLE + " (" + PRODUCT_NAME + ", " + PRODUCT_DESCRIPTION + ", " + PRODUCT_PRICE + ", " + PRODUCT_STOCK + ") " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getProductName(), product.getProductDescription(), product.getProductPrice(), product.getProductStock());
        return product;
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM " + PRODUCT_TABLE + " WHERE " + PRODUCT_ID + " = ?";
        jdbcTemplate.update(sql, id);
    }


    public Product updateProduct(Product product) {
        String sql = "UPDATE " + PRODUCT_TABLE + " SET " + PRODUCT_NAME + " = ?, " + PRODUCT_DESCRIPTION + " = ?, " + PRODUCT_PRICE + " = ?, " + PRODUCT_STOCK + " = ? " +
                "WHERE " + PRODUCT_ID + " = ?";

        jdbcTemplate.update(sql, product.getProductName(), product.getProductDescription(), product.getProductPrice(), product.getProductStock(), product.getProductId());
        return product;
    }
}
