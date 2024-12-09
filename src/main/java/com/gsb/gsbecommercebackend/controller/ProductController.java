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
            return ResponseEntity.status(500).build();
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

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        System.out.println("ID reçu dans l'URL : " + id);
        System.out.println("Nom du produit reçu : " + product.getProductName());

        if (product.getProductId() != id) {
            return ResponseEntity.status(400).body(null);
        }

        try {
            Product updatedProduct = productService.updateProduct(product);
            return ResponseEntity.status(200).body(updatedProduct);
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour du produit : " + e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }




    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
