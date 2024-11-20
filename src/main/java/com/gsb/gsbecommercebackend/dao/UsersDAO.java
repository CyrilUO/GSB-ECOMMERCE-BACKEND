package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.model.Users;
import com.gsb.gsbecommercebackend.model.builder.UsersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gsb.gsbecommercebackend.constant.AppConstants.UserDataSource.*;

@Repository
public class UsersDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Users> getAllUsers() {
        String sql = " SELECT * FROM " + USERS_TABLE;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UsersBuilder usersBuilder = new UsersBuilder();

            return usersBuilder
                    .withId(rs.getInt(USER_ID))
                    .withSurname(rs.getString(USER_SURNAME))
                    .withName(rs.getString(USER_NAME))
                    .withEmail(rs.getString(USER_EMAIL))
                    .withPassword(rs.getString(USER_PASSWORD))
                    .withRole(rs.getString(USER_ROLE))
                    .withDateCreation(rs.getTimestamp(USER_DATE_CREATION).toLocalDateTime())
                    .withModifiedAt(rs.getTimestamp(USER_MODIFIED_AT).toLocalDateTime())
                    .build();
        });
    }
}
