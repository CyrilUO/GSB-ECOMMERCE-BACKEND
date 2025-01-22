package com.gsb.gsbecommercebackend.customExceptions.users;

public class UsersServiceException extends RuntimeException {
    public UsersServiceException(String message) {
        super(message);
    }

    public UsersServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

