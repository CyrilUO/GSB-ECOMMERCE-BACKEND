//package com.gsb.gsbecommercebackend.controller;
//
//
//import com.gsb.gsbecommercebackend.model.Users;
//import com.gsb.gsbecommercebackend.service.UsersService;
//import org.apache.logging.log4j.util.SystemPropertiesPropertySource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
//public class UsersController {
//
//    @Autowired
//    private UsersService usersService;
//
//    @GetMapping("/users")
//    public ResponseEntity <List<Users>> getAllUsers(){
//        try {
//            return ResponseEntity.getAllUsers().isEmpty ?
//                    ResponseEntity.status(404).build() :
//                    ResponseEntity.ok(usersService.getAllUsers());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.status(500).build();
//        }
//    }
//
////    @PostMapping("/users")
////    @PutMapping("/users")
////    @DeleteMapping("/users")
//

