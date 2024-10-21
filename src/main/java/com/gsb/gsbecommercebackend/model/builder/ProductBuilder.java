package com.gsb.gsbecommercebackend.model.builder;

import com.gsb.gsbecommercebackend.model.Product;

/* Design pattern to construct complex objects step by step */

public class ProductBuilder {

    private final Product product;

    public ProductBuilder(){
        this.product = new Product();
    }

    public ProductBuilder withId(int id){
        this.product.setProductId(id);
        return this;
    }

    public ProductBuilder withName(String name){
        this.product.setProductName(name);
        return this;
    }

    public ProductBuilder withDescription(String description){
        this.product.setProductDescription(description);
        return this;
    }

    public ProductBuilder withPrice(double price){
        this.product.setProductPrice(price);
        return this;
    }

    public ProductBuilder withStock(int stock){
        this.product.setProductStock(stock);
        return this;
    }

    public Product build(){
        return this.product;
    }
}
