-- Insertion dans la table users
INSERT INTO users (user_id, user_name, user_surname, user_email, user_password, user_role, user_date_creation, user_modified_at)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password123', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertion dans la table delivery_address
INSERT INTO delivery_address (delivery_address_id, delivery_address_city, delivery_address_street, delivery_address_zip_code, delivery_address_country)
VALUES (1, 'Paris', '123 Main St', 75000, 'France');

-- Insertion dans la table orders
INSERT INTO orders (user_id, order_status, order_total_price, delivery_address_id)
VALUES (1, 'En attente', 100.00, 1);

-- Insertion dans la table products
INSERT INTO products (product_id, product_name, product_description, product_price, product_stock)
VALUES (1, 'Laptop', 'High-end gaming laptop', 1500.00, 10);

-- Insertion dans la table ordered_items
INSERT INTO ordered_items (product_id, ordered_items_quantity, ordered_items_unit_price, order_id)
VALUES (1, 2, 25.00, 1);
