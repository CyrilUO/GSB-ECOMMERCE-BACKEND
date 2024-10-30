package com.gsb.gsbecommercebackend.model;

import java.util.Date;

public class Users {

    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPassword;
    private String userRole;
    private Date userDateCreation;

    public Users(){};

    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getUserDateCreation() {
        return userDateCreation;
    }

    public void setUserDateCreation(Date userDateCreation) {
        this.userDateCreation = userDateCreation;
    }




}

