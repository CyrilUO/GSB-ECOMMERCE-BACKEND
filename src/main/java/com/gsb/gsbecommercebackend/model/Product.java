package com.gsb.gsbecommercebackend.model;

public class Product {

    private int productId;
    private String productName;
    private String productDescription;
    private Float productPrice;
    private int productStock;

    public Product() {

    }


    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

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

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public String toString() {
        return productName + " : " + productId;
    }

    public boolean equals(Product obj) {
        return this.productId == obj.productId;
    }
}
