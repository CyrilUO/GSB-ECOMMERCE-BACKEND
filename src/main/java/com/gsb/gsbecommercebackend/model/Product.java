package com.gsb.gsbecommercebackend.model;

public class Product {

    private int productId;
    private String productName;
    private String productDescription;
    private Double productPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String toString() {
        return productName + " : " + productId;
    }

    public boolean equals(Product obj) {
        return this.productId == obj.productId;
    }
}
