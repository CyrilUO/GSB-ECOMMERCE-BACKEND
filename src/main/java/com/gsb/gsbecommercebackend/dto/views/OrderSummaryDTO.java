package com.gsb.gsbecommercebackend.dto.views;

import com.gsb.gsbecommercebackend.model.deliveryAddressClass.DeliveryAddress;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import com.gsb.gsbecommercebackend.model.usersClass.Users;

public class OrderSummaryDTO {
    private Users users;
    private Order order;
    private DeliveryAddress deliveryAddress;

    public OrderSummaryDTO(Users users, Order order, DeliveryAddress deliveryAddress){
        this.users = users;
        this.order = order;
        this.deliveryAddress = deliveryAddress;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

}
