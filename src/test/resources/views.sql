CREATE VIEW detailedordersummary AS
SELECT u.user_id, u.user_name, u.user_surname, u.user_email,
       o.order_id, o.order_status, o.order_created_at,
       p.product_name, oi.ordered_items_quantity AS quantity,
       oi.ordered_items_unit_price AS unit_price,
       (oi.ordered_items_quantity * oi.ordered_items_unit_price) AS total_item_price,
       o.order_total_price AS total_order_price,
       da.delivery_address_city AS city, da.delivery_address_street AS street,
       da.delivery_address_zip_code AS zip_code, da.delivery_address_country AS country
FROM users u
         JOIN orders o ON u.user_id = o.user_id
         JOIN ordered_items oi ON o.order_id = oi.order_id
         JOIN products p ON oi.product_id = p.product_id
         JOIN delivery_address da ON o.delivery_address_id = da.delivery_address_id;

CREATE VIEW ordersummary AS
SELECT u.user_id, u.user_name, u.user_surname, u.user_email,
       o.order_id, o.order_total_price, o.order_status,
       o.delivery_address_id, da.delivery_address_city,
       da.delivery_address_street, da.delivery_address_zip_code, da.delivery_address_country
FROM users u
         JOIN orders o ON u.user_id = o.user_id
         JOIN delivery_address da ON o.delivery_address_id = da.delivery_address_id;
