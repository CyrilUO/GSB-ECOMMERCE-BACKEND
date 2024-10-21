package com.gsb.gsbecommercebackend.controller;


import com.gsb.gsbecommercebackend.dao.ProductDAO;
import com.gsb.gsbecommercebackend.model.Product;
import com.gsb.gsbecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Pas de DAO */

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return productService.getAllProducts().isEmpty() ?
                    ResponseEntity.status(404).build() :
                    ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.addProduct(product);
            return newProduct != null
                    ? ResponseEntity.status(201).body(newProduct)
                    : ResponseEntity.status(400).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

//    @PutMapping("/products/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
//        Product updatedProduct = productService.updateProduct(id, product);
//        return ResponseEntity.status(201).body(updatedProduct);
//    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
