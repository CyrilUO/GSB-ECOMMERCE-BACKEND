package com.gsb.gsbecommercebackend.model;

import java.time.LocalDateTime;

public class Users {

    private int userId;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPassword;
    private String userRole;
    private LocalDateTime userDateCreation;
    private LocalDateTime userModifiedAt;

    public Users(){};

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDateTime getUserDateCreation() {
        return userDateCreation;
    }

    public void setUserDateCreation(LocalDateTime userDateCreation) {
        this.userDateCreation = userDateCreation;
    }

    public LocalDateTime getUserModifiedAt() { return userModifiedAt; }

    public void setUserModifiedAt(LocalDateTime userModifiedAt) { this.userModifiedAt = userModifiedAt; }

    /* Note à moi même : la méthode toString redéfinie ou surcharge une méthode de la classe parent (superclass : Object)
     * Ici elle surcharge la méthode toString qui est native à l'objet, on redéfini son comportement
     * Si l'on voulait créer notre propre méthode sans override cette dernière nous aurions pu utiliser
     * String ToString() et enlever l'annotation. On peut faire de même avec les méthodes equalsTo() ou hashCode() */
    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userDateCreation=" + userDateCreation +
                ", modifiedAt=" + userModifiedAt +
                '}';
    }

}

