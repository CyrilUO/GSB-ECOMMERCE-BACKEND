package com.gsb.gsbecommercebackend.controller.products;

import com.gsb.gsbecommercebackend.model.productsClass.Product;
import com.gsb.gsbecommercebackend.model.usersClass.Users;
import com.gsb.gsbecommercebackend.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductAnalyticsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/stocks")
    public ResponseEntity<List<Map<String, Object>>> getProductStockStats() {
        try {
            return ResponseEntity.ok(productService.getCurrentProductStock());

        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du produit : " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

}


