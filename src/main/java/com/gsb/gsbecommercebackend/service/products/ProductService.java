package com.gsb.gsbecommercebackend.service.products;

/* Lien entre controlleur et DAO */
/* Injection de dépendance */

import com.gsb.gsbecommercebackend.dao.products.ProductDAO;
import com.gsb.gsbecommercebackend.model.productsClass.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/* La logique c'est dans les services */

@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {

        this.productDAO = productDAO;
    }

    public void deleteProduct(int id){
        productDAO.deleteProduct(id);
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



    public List<Map<String, Object>> getCurrentProductStock() {
        return productDAO.getCurrentProductStock();
    }



    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }
}
