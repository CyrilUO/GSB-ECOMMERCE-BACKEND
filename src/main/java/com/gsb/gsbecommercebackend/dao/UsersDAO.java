package com.gsb.gsbecommercebackend.dao;

import com.gsb.gsbecommercebackend.model.Users;
import com.gsb.gsbecommercebackend.model.builder.UsersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.gsb.gsbecommercebackend.constant.AppConstants.UserDataSource.*;

@Repository
public class UsersDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Users> getAllUsers() {
        String sql = " SELECT * FROM " + USERS_TABLE;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UsersBuilder usersBuilder = new UsersBuilder();
            System.out.println("SQL Parameter: " + rs + " " + rowNum);


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

    public Users addUser(Users users) {
        String sql = "INSERT INTO " + USERS_TABLE + " (" +
                USER_NAME + ", " +
                USER_SURNAME + ", " +
                USER_EMAIL + ", " +
                USER_PASSWORD + ", " +
                USER_ROLE + ") " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                users.getUserName(),
                users.getUserSurname(),
                users.getUserEmail(),
                users.getUserPassword(),
                users.getUserRole()
        );

        return users;
    }


    public Users updateUser(Users users) {
        String sql = " UPDATE " + USERS_TABLE +
                " SET " + USER_SURNAME + " = ?,"
                + USER_NAME + " = ?,"
                + USER_EMAIL + " = ?,"
                + USER_PASSWORD + " = ?,"
                + USER_ROLE + " = ?,"
                + USER_MODIFIED_AT + " = ?" +
                "WHERE " + USER_ID + " = ?";
        jdbcTemplate.update(sql,
                users.getUserSurname(),
                users.getUserName(),
                users.getUserEmail(),
                users.getUserPassword(),
                users.getUserRole(),
                users.getUserModifiedAt(),
                users.getUserId());

        return users;
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM " + USERS_TABLE + " WHERE " + USER_ID + " = ?";
        jdbcTemplate.update(sql, id);
    }


    public Users findByEmail(String email) {
        String sql = "SELECT * FROM " + USERS_TABLE + " WHERE " + USER_EMAIL + " = ? ";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Users.class), email);
    }

    public Users findUserById(int id) {
        String sql = "SELECT * FROM" + USERS_TABLE + " WHERE " + USER_ID + " = ? ";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Users.class), id);
    }

    public List<Map<String, Object>> getUsersStatByDay() {
        String sql = "SELECT DATE(" + USER_DATE_CREATION + ") AS creation_date, COUNT(*) AS user_count " +
                "FROM " + USERS_TABLE + " " +
                "GROUP BY DATE(" + USER_DATE_CREATION + ") " +
                "ORDER BY creation_date";

        return jdbcTemplate.queryForList(sql);
    }







}
