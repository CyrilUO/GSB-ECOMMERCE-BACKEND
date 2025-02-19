package com.gsb.gsbecommercebackend.controller.deliveryAddress;//package com.gsb.gsbecommercebackend.controller.deliveryAddress;
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class DeliveryAddressController {
//
//    @GetMapping("/deliveryAddress/")
//}

import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.model.deliveryAddressClass.DeliveryAddress;
import com.gsb.gsbecommercebackend.service.deliveryAddress.DeliveryAddressService;
import com.gsb.gsbecommercebackend.service.orders.OrderAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @GetMapping("/full-list")
    public ResponseEntity<List<DeliveryAddress>> getAllAddresses() {
        try {
            List<DeliveryAddress> deliveryAddresses = deliveryAddressService.getAllAddresses();
            return ResponseEntity.ok(deliveryAddresses);
        } catch (DataAccessException e) {
            String errorMessage = "Erreur lors de la récupération de tous les utilisateurs.";
            System.err.println(errorMessage + " : " + e.getMessage());
            throw new DaoException(errorMessage, e);
        }
    }
}