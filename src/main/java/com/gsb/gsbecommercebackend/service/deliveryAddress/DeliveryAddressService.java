package com.gsb.gsbecommercebackend.service.deliveryAddress;

import com.gsb.gsbecommercebackend.dao.deliveryAddress.DeliveryAddressDAO;
import com.gsb.gsbecommercebackend.model.deliveryAddressClass.DeliveryAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAddressService {

    private final DeliveryAddressDAO deliveryAddressDAO;

    public DeliveryAddressService(DeliveryAddressDAO deliveryAddressDAO){
        this.deliveryAddressDAO = deliveryAddressDAO;
    }

    public List<DeliveryAddress> getAllAddresses() {
        return deliveryAddressDAO.getAllAddresses();
    }

}
