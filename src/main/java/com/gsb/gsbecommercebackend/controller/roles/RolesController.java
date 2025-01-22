package com.gsb.gsbecommercebackend.controller.roles;


import com.gsb.gsbecommercebackend.model.rolesClass.Roles;
import com.gsb.gsbecommercebackend.service.roles.RolesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolesController {

    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {this.rolesService = rolesService;}

    @GetMapping("/roles")
    public ResponseEntity<List<Roles>> getRoles() {
        try {
            List<Roles> roles = rolesService.getRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
