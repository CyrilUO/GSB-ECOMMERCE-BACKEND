package com.gsb.gsbecommercebackend.controller.users;


import com.gsb.gsbecommercebackend.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsersAnalyticsController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users/stats")
    public ResponseEntity<List<Map<String, Object>>> getUserStatsByDay() {
        System.out.println("Requête reçue pour GET /users/stats");
        try {
            List<Map<String, Object>> stats = usersService.getUsersStatsByDay();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des statistiques : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
