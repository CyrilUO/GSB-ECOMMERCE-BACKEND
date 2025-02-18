package com.gsb.gsbecommercebackend.dao.orders;

import com.gsb.gsbecommercebackend.customExceptions.orders.OrderCreationException;
import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.*;
import static com.gsb.gsbecommercebackend.constant.AppConstants.OrderDataSource.ORDER_ID;
import static com.gsb.gsbecommercebackend.constant.OrdersConstant.DetailedOrderSummaryViewDataSource.*;
import static com.gsb.gsbecommercebackend.constant.OrdersConstant.DetailedOrderSummaryViewDataStringObject.*;


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

        String sql = "SELECT * FROM " + VIEW_NAME + " WHERE " + USER_ID + " = ? ORDER BY " + ORDER_ID + " DESC";

        System.out.println("Requête SQL exécutée : " + sql + ", userId : " + userId);

        try {
            // Grouper les commandes et leurs produits
            Map<Integer, Map<String, Object>> orders = new LinkedHashMap<>();

            jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
                try {
                    int orderId = rs.getInt(ORDER_ID);

                    // Commande existante ou nouvelle
                    Map<String, Object> order = orders.computeIfAbsent(orderId, id -> {
                        Map<String, Object> newOrder = new HashMap<>();
                        try {
                            newOrder.put(USER_ID_OBJECT, rs.getInt(USER_ID));
                            newOrder.put(USER_NAME_OBJECT, rs.getString(USER_NAME));
                            newOrder.put(USER_SURNAME_OBJECT, rs.getString(USER_SURNAME));
                            newOrder.put(USER_EMAIL_OBJECT, rs.getString(USER_EMAIL));
                            newOrder.put(ORDER_ID_OBJECT, id);
                            newOrder.put(TOTAL_ORDER_PRICE_OBJECT, rs.getBigDecimal(TOTAL_ORDER_PRICE));
                            newOrder.put(CITY_OBJECT, rs.getString(CITY));
                            newOrder.put(STREET_OBJECT, rs.getString(STREET));
                            newOrder.put(ZIP_CODE_OBJECT, rs.getInt(ZIP_CODE));
                            newOrder.put(COUNTRY_OBJECT, rs.getString(COUNTRY));
                            newOrder.put(ORDER_CREATED_AT_OBJECT, rs.getDate(ORDER_CREATED_AT));
                            newOrder.put(ORDER_STATUS_OBJECT, rs.getString(ORDER_STATUS));
                            newOrder.put(ITEMS_OBJECT, new ArrayList<Map<String, Object>>());

                            System.out.println("Commande validée avec en données : " + newOrder);

                        } catch (SQLException e) {
                            throw new RuntimeException("Erreur lors de l'extraction des données de la commande : " + e.getMessage(), e);
                        }
                        return newOrder;
                    });

                    // Ajouter le produit
                    List<Map<String, Object>> items = (List<Map<String, Object>>) order.get(ITEMS_OBJECT);
                    Map<String, Object> item = new HashMap<>();
                    try {
                        item.put(PRODUCT_NAME_OBJECT, rs.getString(PRODUCT_NAME));
                        item.put(QUANTITY_OBJECT, rs.getBigDecimal(QUANTITY));
                        item.put(UNIT_PRICE_OBJECT, rs.getBigDecimal(UNIT_PRICE));
                        item.put(TOTAL_ITEM_PRICE_OBJECT, rs.getBigDecimal(TOTAL_ITEM_PRICE));
                        items.add(item);
                    } catch (SQLException e) {
                        throw new RuntimeException("Erreur lors de l'extraction des données du produit : " + e.getMessage(), e);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Erreur lors de l'extraction des données de la commande ou du produit : " + e.getMessage(), e);
                }

                System.out.println(orders);
            });

            // Retourner les commandes
            return new ArrayList<>(orders.values());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les données des commandes pour l'utilisateur " + userId, e);
        }
    }








    /* Creation de vue pour créer des requêtes complexes sur différentes tables afin de manipuler des objets*/
//    CREATE VIEW CompactOrderSummary AS
//    SELECT
//    u.user_id,
//    u.user_name,
//    u.user_surname,
//    u.user_email,
//    o.order_id,
//    o.order_total_price AS total_order_price,
//    o.delivery_address_id
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
//
//    CREATE VIEW DetailedOrderSummary AS
//    SELECT
//    u.user_id,
//    u.user_name,
//    u.user_surname,
//    u.user_email,
//    o.order_id,
//    o.order_status,
//    o.order_created_at,
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

