package com.gsb.gsbecommercebackend.service;

/* Lien entre controlleur et DAO */
/* Injection de dépendance */

import com.gsb.gsbecommercebackend.dao.ProductDAO;
import com.gsb.gsbecommercebackend.model.Product;
import org.apache.el.util.ExceptionUtils;
import org.springframework.stereotype.Service;

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
            throw new Exception();
        }
        return productDAO.getAllProducts();
    }
}
