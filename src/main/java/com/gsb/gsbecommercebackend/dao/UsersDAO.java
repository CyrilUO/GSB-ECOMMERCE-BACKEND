//package com.gsb.gsbecommercebackend.dao;
//
//import com.gsb.gsbecommercebackend.constant.AppConstants;
//
//
//import com.gsb.gsbecommercebackend.model.builder.UsersBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import static com.gsb.gsbecommercebackend.constant.AppConstants.UserDataSource.USER_TABLE;
//
//public class UsersDAO {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public List<Users> getAllUsers() {
//        String sql = " SELECT * FROM " + USER_TABLE;
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            UsersBuilder usersBuilder = new UsersBuilder();
//
//            return usersBuilder
//                    .
//        })
//    }
//}
