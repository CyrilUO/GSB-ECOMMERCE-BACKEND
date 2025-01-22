package com.gsb.gsbecommercebackend.dao.deliveryAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.gsb.gsbecommercebackend.constant.AppConstants.DeliveryAddressDataSource.*;


@Repository
public class DeliveryAddressDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DeliveryAddressDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getDeliveryAddressNameById(int addressId) {
        String sql = "SELECT CONCAT(" + DELIVERY_ADDRESS_STREET + ", ', ', "
                + DELIVERY_ADDRESS_CITY + ", ', ', "
                + DELIVERY_ADDRESS_ZIP_CODE + ", ', ', "
                + DELIVERY_ADDRESS_COUNTRY + ") AS fullAddress "
                + "FROM " + DELIVERY_ADDRESS_TABLE + " WHERE " + DELIVERY_ADDRESS_ID + " = ? ";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, addressId);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Aucune adresse trouvée avec l'ID : " + addressId);
            return null; // Ou "Adresse inconnue"
        }
    }
}
