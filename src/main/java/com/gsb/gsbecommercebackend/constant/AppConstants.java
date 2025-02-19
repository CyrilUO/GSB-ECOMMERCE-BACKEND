package com.gsb.gsbecommercebackend.constant;

public class AppConstants {

    public static class ProductDataSource{
        public final static String PRODUCT_TABLE = "products";
        public final static String PRODUCT_ID = "product_id";
        public final static String PRODUCT_NAME = "product_name";
        public final static String PRODUCT_DESCRIPTION = "product_description";
        public final static String PRODUCT_PRICE = "product_price";
        public final static String PRODUCT_STOCK = "product_stock";
    }

    public static class UserDataSource{
        public final static String USERS_TABLE = "users";
        public final static String USER_ID = "user_id";
        public final static String USER_NAME = "user_name";
        public final static String USER_SURNAME = "user_surname";
        public final static String USER_EMAIL = "user_email";
        public final static String USER_PASSWORD = "user_password";
        public final static String USER_ROLE = "user_role";
        public final static String USER_DATE_CREATION = "user_date_creation";
        public final static String USER_MODIFIED_AT = "user_modified_at";
    }

    public static class DeliveryAddressDataSource{
        public final static String DELIVERY_ADDRESS_TABLE = "delivery_address";
        public final static String DELIVERY_ADDRESS_ID = "delivery_address_id";
        public final static String DELIVERY_ADDRESS_CITY = "delivery_address_city";
        public final static String DELIVERY_ADDRESS_STREET = "delivery_address_street";
        public final static String DELIVERY_ADDRESS_ZIP_CODE = "delivery_address_zip_code";
        public final static String DELIVERY_ADDRESS_COUNTRY = "delivery_address_country";
    }

    public static class OrderDataSource {
        public final static String ORDERS_TABLE = "orders";                // Nom de la table
        public final static String ORDER_ID = "order_id";                 // Clé primaire
        public final static String USER_ID = "user_id";                   // Clé étrangère vers users
        public final static String ORDER_STATUS = "order_status";         // Statut de la commande
        public final static String ORDER_TOTAL_PRICE = "order_total_price"; // Prix total de la commande
        public final static String DELIVERY_ADDRESS_ID = "delivery_address_id"; // Adresse de livraison
        public final static String ORDER_CREATED_AT = "order_created_at"; // Date de création
        public final static String ORDER_UPDATED_AT = "order_updated_at"; // Date de mise à jour
    }

    public static class OrderedItemsDataSource {
        public final static String ORDERED_ITEMS_TABLE = "ordered_items";           // Nom de la table
        public final static String ORDERED_ITEMS_ID = "ordered_items_id";          // Clé primaire
        public final static String PRODUCT_ID = "product_id";                      // Clé étrangère vers products
        public final static String ORDERED_ITEMS_QUANTITY = "ordered_items_quantity"; // Quantité commandée
        public final static String ORDERED_ITEMS_UNIT_PRICE = "ordered_items_unit_price"; // Prix unitaire
        public final static String ORDER_ID = "order_id";                          // Clé étrangère vers orders
    }

    public static class RolesDataSource {
        public final static String ROLES_TABLE = "roles";
        public final static String ROLE_ID = "role_id";
        public final static String ROLE_NAME = "role_name";
    }

    public static class JWTServiceParameters {
        public final static long EXPIRATION_TIME = 86400000;
        public final static String CLAIM_ROLE = "roleName";
        public final static String CLAIM_USERID = "userId";

    }


    public static class OrderStatusEnum {
        public final static String ORDER_AWAITING = "En attente";
        public final static String ORDER_SENT = "Expédié";
        public final static String ORDER_SHIPPED = "Livré";
    }

    public static class RolesEnum {
        public final static String ADMIN = "admin";
        public final static String MEDICAL_EMPLOYEE = "medical-employee";
        public final static String SALESPERSON = "salesperson";
    }

}
