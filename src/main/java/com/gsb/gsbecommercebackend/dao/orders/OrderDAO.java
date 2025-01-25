package com.gsb.gsbecommercebackend.dao.orders;

import com.gsb.gsbecommercebackend.customExceptions.orders.OrderCreationException;
import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.*;
import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.ORDER_ID;
import static com.gsb.gsbecommercebackend.constant.OrdersConstant.DetailedOrderSummaryView.*;


@Repository
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer createOrder(Order order) {
        String sql = "INSERT INTO "
                + ORDERS_TABLE + " ("
                + USER_ID + ", "
                + ORDER_STATUS + ", "
                + ORDER_TOTAL_PRICE + ", "
                + DELIVERY_ADDRESS_ID
                + ") VALUES (?, ?, ?, ?)";

        System.out.println("Création de la commande avec : " + order);


        System.out.println("Insertion de la commande avec les valeurs : " +
                order.getUserId() + ", " + order.getOrderStatus() + ", " +
                order.getOrderTotalPrice() + ", " + order.getDeliveryAddressId() + ", " +
                order.getOrderCreatedAt());


        jdbcTemplate.update(
                sql,
                order.getUserId(),
                order.getOrderStatus(),
                order.getOrderTotalPrice(),
                order.getDeliveryAddressId()
        );


        // Récupérer l'ID généré
        Integer generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        System.out.println("Order ID généré (DAO) : " + generatedId);
        if (generatedId == null || generatedId == 0) {
            throw new OrderCreationException("Échec de la récupération de l'ID généré !");
        }


        return generatedId; // Retourne -1 en cas d'erreur
    }


    public Order getOrderDetailsByOrderId(int orderId) {
        String sql = "SELECT * FROM " + ORDERS_TABLE + " WHERE " + ORDER_ID + "= ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), orderId);

    }

    public List<Map<String, Object>> getDetailedOrdersByUserId(Integer userId) {

        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur (userId) ne peut pas être null.");
        }
        // Utilisez la vue OrderSummary pour plus de détails
        String sql = "SELECT * FROM " + VIEW_NAME + " WHERE " + USER_ID + " = ? ORDER BY " + ORDER_ID + " DESC";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Map<String, Object> row = new HashMap<>();
                row.put(USER_NAME, rs.getString(USER_NAME));
                row.put(USER_SURNAME, rs.getString(USER_SURNAME));
                row.put(USER_EMAIL, rs.getString(USER_EMAIL));
                row.put(ORDER_ID, rs.getInt(ORDER_ID));
                row.put(PRODUCT_NAME, rs.getString(PRODUCT_NAME));
                row.put(QUANTITY, rs.getBigDecimal(QUANTITY));
                row.put(UNIT_PRICE, rs.getBigDecimal(UNIT_PRICE));
                row.put(TOTAL_ITEM_PRICE, rs.getBigDecimal(TOTAL_ITEM_PRICE));
                row.put(TOTAL_ORDER_PRICE, rs.getBigDecimal(TOTAL_ORDER_PRICE));
                row.put(CITY, rs.getString(CITY));
                row.put(STREET, rs.getString(STREET));
                row.put(ZIP_CODE, rs.getInt(ZIP_CODE));
                row.put(COUNTRY, rs.getString(COUNTRY));

                System.out.println("Insertion de la commande avec les valeurs : ");
                System.out.println("Nom utilisateur : " + row.get(USER_NAME));
                System.out.println("Prénom utilisateur : " + row.get(USER_SURNAME));
                System.out.println("Email utilisateur : " + row.get(USER_EMAIL));
                System.out.println("ID commande : " + row.get(ORDER_ID));
                System.out.println("Nom du produit : " + row.get(PRODUCT_NAME));
                System.out.println("Quantité : " + row.get(QUANTITY));
                System.out.println("Prix unitaire : " + row.get(UNIT_PRICE));
                System.out.println("Prix total des articles : " + row.get(TOTAL_ITEM_PRICE));
                System.out.println("Prix total de la commande : " + row.get(TOTAL_ORDER_PRICE));
                System.out.println("Ville : " + row.get(CITY));
                System.out.println("Rue : " + row.get(STREET));
                System.out.println("Code postal : " + row.get(ZIP_CODE));
                System.out.println("Pays : " + row.get(COUNTRY));


                return row;

            }, userId);
        } catch (DaoException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les données des commandes pour l'utilisateur " + userId, e);
        }
    }





    /* Creation de vue pour créer des requêtes complexes sur différentes tables afin de manipuler des objets*/
//    CREATE VIEW CompactOrderSummary AS
//    SELECT
//    u.user_name,
//    u.user_surname,
//    u.user_email,
//    o.order_id,
//    o.order_total_price AS total_order_price,
//    da.delivery_address_city AS city,
//    da.delivery_address_street AS street,
//    da.delivery_address_zip_code AS zip_code,
//    da.delivery_address_country AS country
//            FROM
//    users u
//    JOIN
//    orders o ON u.user_id = o.user_id
//            JOIN
//    delivery_address da ON o.delivery_address_id = da.delivery_address_id;

//    CREATE VIEW DetailedOrderSummary AS
//    SELECT
//    u.user_name,
//    u.user_surname,
//    u.user_email,
//    o.order_id,
//    p.product_name,
//    oi.ordered_items_quantity AS quantity,
//    oi.ordered_items_unit_price AS unit_price,
//            (oi.ordered_items_quantity * oi.ordered_items_unit_price) AS total_item_price,
//    o.order_total_price AS total_order_price,
//    da.delivery_address_city AS city,
//    da.delivery_address_street AS street,
//    da.delivery_address_zip_code AS zip_code,
//    da.delivery_address_country AS country
//            FROM
//    users u
//    JOIN
//    orders o ON u.user_id = o.user_id
//            JOIN
//    ordered_items oi ON o.order_id = oi.order_id
//            JOIN
//    products p ON oi.product_id = p.product_id
//            JOIN
//    delivery_address da ON o.delivery_address_id = da.delivery_address_id;


}

