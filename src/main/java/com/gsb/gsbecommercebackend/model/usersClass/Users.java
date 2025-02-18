package com.gsb.gsbecommercebackend.model.usersClass;

import com.gsb.gsbecommercebackend.model.rolesClass.Roles;

import java.time.LocalDateTime;

public class Users {

    private int userId;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPassword;
    private int roleId; // Champ temporaire pour mapper directement le roleId du payload
    private LocalDateTime userDateCreation;
    private LocalDateTime userModifiedAt;

    private Roles role; // Relation avec la classe Roles

    public Users(){};

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getRoleId() {
        return this.role != null ? this.role.getRoleId() : this.roleId; // Si rôle est null, retournez roleId
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
        if (this.role == null) {
            this.role = new Roles();
        }
        this.role.setRoleId(roleId);
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


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
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
    /* Ici on override le print en console pour l'instaciation d'un objet dérivé de la classe User*/
    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", roleId=" + (role != null ? role.getRoleId() : "null") +
                ", userDateCreation=" + userDateCreation +
                ", modifiedAt=" + userModifiedAt +
                '}';
    }



}

