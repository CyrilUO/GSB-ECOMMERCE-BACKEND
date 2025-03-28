package com.gsb.gsbecommercebackend.controller.products;

import com.gsb.gsbecommercebackend.model.productsClass.Product;
import com.gsb.gsbecommercebackend.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Pas de DAO */

@RequestMapping("/api")
@RestController
public class ProductCRUDController {

    @Autowired
    private ProductService productService;

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        System.out.println("Requête reçue pour DELETE /products/" + id);
        try {
            productService.deleteProduct(id);
            System.out.println("Produit supprimé avec succès : ID = " + id);
            return ResponseEntity.ok("Produit supprimé avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du produit : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur lors de la suppression du produit.");
        }
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {

        System.out.println("Requête reçue pour GET /products");
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("Aucun produit trouvé.");
                return ResponseEntity.status(404).build();
            } else {
                System.out.println("Produits trouvés : " + products.size());
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des produits : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        System.out.println("Requête reçue pour POST /products avec le produit : " + product);

        if (product == null){
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }
        try {
            Product newProduct = productService.addProduct(product);
            if (newProduct != null) {
                System.out.println("Produit ajouté avec succès : " + newProduct);
                return ResponseEntity.status(201).body(newProduct);
            } else {
                System.out.println("Échec de l'ajout du produit.");
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout du produit : " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        System.out.println("Requête GET pour récupérer le produit avec ID : " + id);

        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody Product product) {
        System.out.println("Requête reçue pour PUT /products/" + productId);
        System.out.println("Données du produit reçues : " + product);

        if (product.getProductId() != productId) {
            System.out.println("Erreur : L'ID dans l'URL ne correspond pas à l'ID du produit.");
            return ResponseEntity.status(400).body(null);
        }

        try {
            Product updatedProduct = productService.updateProduct(product);
            System.out.println("Produit mis à jour avec succès : " + updatedProduct);
            return ResponseEntity.status(200).body(updatedProduct);
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour du produit : " + e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

}
