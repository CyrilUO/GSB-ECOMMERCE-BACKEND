package com.gsb.gsbecommercebackend.model.deliveryAddressClass;

public class DeliveryAddress {
    private int deliveryAddressId;
    private String deliveryAddressCity;
    private String deliveryAddressStreet;
    private int deliveryAddressZipCode;
    private String deliveryAddressCountry;

    public int getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(int deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryAddressCity() {
        return deliveryAddressCity;
    }

    public void setDeliveryAddressCity(String deliveryAddressCity) {
        this.deliveryAddressCity = deliveryAddressCity;
    }

    public String getDeliveryAddressStreet() {
        return deliveryAddressStreet;
    }

    public void setDeliveryAddressStreet(String deliveryAddressStreet) {
        this.deliveryAddressStreet = deliveryAddressStreet;
    }

    public int getDeliveryAddressZipCode() {
        return deliveryAddressZipCode;
    }

    public void setDeliveryAddressZipCode(int deliveryAddressZipCode) {
        this.deliveryAddressZipCode = deliveryAddressZipCode;
    }

    public String getDeliveryAddressCountry() {
        return deliveryAddressCountry;
    }

    public void setDeliveryAddressCountry(String deliveryAddressCountry) {
        this.deliveryAddressCountry = deliveryAddressCountry;
    }

}
