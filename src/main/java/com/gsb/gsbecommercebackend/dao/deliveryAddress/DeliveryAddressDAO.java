package com.gsb.gsbecommercebackend.dao.deliveryAddress;

import com.gsb.gsbecommercebackend.model.deliveryAddressClass.DeliveryAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public List<DeliveryAddress> getAllAddresses() {
        String sql = "SELECT * FROM " + DELIVERY_ADDRESS_TABLE;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DeliveryAddress.class));
    }

//    public List<DeliveryAddress> getAllAddresses() {
//        String sql = "SELECT * FROM " + DELIVERY_ADDRESS_TABLE;
//        return jdbcTemplate.query(sql, new DeliveryAddressRowMapper());
//    }
//
//    // Classe interne pour mapper les résultats SQL en objets DeliveryAddress
//    private static class DeliveryAddressRowMapper implements RowMapper<DeliveryAddress> {
//        @Override
//        public DeliveryAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
//            DeliveryAddress address = new DeliveryAddress();
//            address.setDeliveryAddressId(rs.getInt("delivery_address_id"));
//            address.setDeliveryAddressCity(rs.getString("delivery_address_city"));
//            address.setDeliveryAddressStreet(rs.getString("delivery_address_street"));
//            address.setDeliveryAddressZipCode(rs.getInt("delivery_address_zip_code"));
//            address.setDeliveryAddressCountry(rs.getString("delivery_address_country"));
//            return address;
//        }
//    }
}
