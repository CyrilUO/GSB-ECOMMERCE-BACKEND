package com.gsb.gsbecommercebackend.customExceptions.orders;

public class OrderCreationException extends RuntimeException {
    public OrderCreationException(String message) {
        super(message);
    }
}

