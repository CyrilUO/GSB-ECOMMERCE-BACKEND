package com.gsb.gsbecommercebackend.service;

/* Lien entre controlleur et DAO */
/* Injection de dépendance */

import com.gsb.gsbecommercebackend.dao.ProductDAO;
import com.gsb.gsbecommercebackend.model.Product;
import com.gsb.gsbecommercebackend.model.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
/* La logique c'est dans les services */

@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() throws Exception {
        if(productDAO.getAllProducts().isEmpty()){
            throw new Exception("There is no product available");
        }
        return productDAO.getAllProducts();
    }

    public Product addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    public Product updateProduct(Product product){
        return productDAO.updateProduct(product);
    }

    public void deleteProduct(int id){
        productDAO.deleteProduct(id);
    }

}
