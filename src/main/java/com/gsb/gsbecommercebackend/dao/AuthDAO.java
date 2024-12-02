package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.gsb.gsbecommercebackend.constant.AppConstants.UserDataSource.*;


@Repository
public class AuthDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Users validateUser(String email, String password) {
        try {
            String sql = "SELECT * FROM " + USERS_TABLE +
                    " WHERE " + USER_EMAIL + " = ? " +
                    "AND " + USER_PASSWORD + " = ?";
            return null;

        } catch (Exception e) {

        }
    };
}