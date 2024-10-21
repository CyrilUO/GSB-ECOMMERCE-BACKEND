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
        public final static String USER_TABLE = "users";
        public final static String USER_ID = "user_id";
        public final static String USER_NAME = "user_name";
        public final static String USER_SURNAME = "user_surname";
        public final static String USER_EMAIL = "user_email";
        public final static String USER_PASSWORD = "user_password";
        public final static String USER_ROLE = "user_role";
        public final static String USER_DATE_CREATION = "user_date_creation";
    }

    public static class DeliveryAddressDataSource{
        public final static String DELIVERY_ADDRESS_TABLE = "delivery_address";
        public final static String DELIVERY_ADDRESS_ID = "delivery_address_id";
        public final static String DELIVERY_ADDRESS_CITY = "delivery_address_id";
        public final static String DELIVERY_ADDRESS_ZIP_CODE = "delivery_address_id";
    }

    public static class OrderDataSource{
        public final static String ORDER_TABLE = "order";
//        public final static String ORDER_TABLE = "order";
//        public final static String ORDER_TABLE = "order";
//        public final static String ORDER_TABLE = "order";
    }

    public static class ItemOrder{
        public final static String ITEM_ORDER_TABLE = "de";
    }
}
