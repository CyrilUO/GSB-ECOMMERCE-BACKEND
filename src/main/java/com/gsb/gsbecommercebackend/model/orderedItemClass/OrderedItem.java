package com.gsb.gsbecommercebackend.model.orderedItemClass;


public class OrderedItem {
    private int orderedItemsId;
    private int productId;
    private int orderedItemsQuantity;
    private Float orderedItemsUnitPrice;
    private int orderId;

    public OrderedItem() {}

    public int getOrderedItemsId() {
        return orderedItemsId;
    }

    public void setOrderedItemsId(int orderedItemsId) {
        this.orderedItemsId = orderedItemsId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderedItemsQuantity() {
        return orderedItemsQuantity;
    }

    public void setOrderedItemsQuantity(int orderedItemsQuantity) {
        this.orderedItemsQuantity = orderedItemsQuantity;
    }

    public Float getOrderedItemsUnitPrice() {
        return orderedItemsUnitPrice;
    }

    public void setOrderedItemsUnitPrice(Float orderedItemsUnitPrice) {
        this.orderedItemsUnitPrice = orderedItemsUnitPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


}
