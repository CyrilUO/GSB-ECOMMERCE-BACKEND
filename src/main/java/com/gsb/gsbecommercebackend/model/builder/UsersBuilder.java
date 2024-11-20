package com.gsb.gsbecommercebackend.model.builder;

import com.gsb.gsbecommercebackend.model.Users;

import java.time.LocalDateTime;


public class UsersBuilder {

    private final Users users;

    public UsersBuilder()  { this.users = new Users(); }

    public UsersBuilder withId(int id) {
        this.users.setUserId(id);
        return this;
    }

    public UsersBuilder withSurname(String surname) {
        this.users.setUserSurname(surname);
        return this;
    }

    public UsersBuilder withName(String name) {
        this.users.setUserName(name);
        return this;
    }

    public UsersBuilder withEmail(String email) {
        this.users.setUserEmail(email);
        return this;
    }

    public UsersBuilder withPassword(String password) {
        this.users.setUserPassword(password);
        return this;
    }

    public UsersBuilder withRole(String role) {
        this.users.setUserRole(role);
        return this;
    }

    public UsersBuilder withDateCreation(LocalDateTime dateCreation) {
        this.users.setUserDateCreation(dateCreation);
        return this;
    }

    public UsersBuilder withModifiedAt(LocalDateTime modifiedAt) {
        this.users.setUserModifiedAt(modifiedAt);
        return this;
    }


    public Users build(){
        return this.users;
    }
}
