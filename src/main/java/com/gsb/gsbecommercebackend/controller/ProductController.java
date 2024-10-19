package com.gsb.gsbecommercebackend.controller;


import com.gsb.gsbecommercebackend.dao.ProductDAO;
import com.gsb.gsbecommercebackend.model.Product;
import com.gsb.gsbecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/* Pas de DAO */

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        try{
            return productService.getAllProducts().isEmpty() ?
                    ResponseEntity.status(404).build():
                    ResponseEntity.ok(productService.getAllProducts());
        }catch (Exception e){
            return null;
        }

    }
}
