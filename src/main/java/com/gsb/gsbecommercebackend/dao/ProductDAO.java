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

    /* Static permet d'accéder à des fonctions ou propriétés de classe n'étant pas encore instantié, ça dépend pas de l'objet*/

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM " + PRODUCT_TABLE;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProductBuilder builder = new ProductBuilder();

            return builder.withId(rs.getInt(PRODUCT_ID))
                    .withName(rs.getString(PRODUCT_NAME))
                    .build();
        });
    }
}
