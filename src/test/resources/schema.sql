CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       user_name VARCHAR(100),
                       user_surname VARCHAR(100),
                       user_email VARCHAR(100) UNIQUE,
                       user_password VARCHAR(100),
                       user_role VARCHAR(50)
);

CREATE TABLE delivery_address (
                                  delivery_address_id INT AUTO_INCREMENT PRIMARY KEY,
                                  delivery_address_city VARCHAR(100),
                                  delivery_address_street VARCHAR(100),
                                  delivery_address_zip_code INT,
                                  delivery_address_country VARCHAR(100)
);
-- Création de la table product
CREATE TABLE product (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,
                         product_name VARCHAR(100) NOT NULL,
                         product_description VARCHAR(100) NOT NULL,
                         product_price DECIMAL(10,2) NOT NULL,
                         product_stock INT NOT NULL
);

CREATE TABLE orders (
                        order_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        order_status VARCHAR(50),
                        order_total_price DECIMAL(10,2),
                        delivery_address_id INT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(user_id),
                        FOREIGN KEY (delivery_address_id) REFERENCES delivery_address(delivery_address_id)
);

-- Création de la table ordered_items
CREATE TABLE ordered_items (
                               ordered_items_id INT AUTO_INCREMENT PRIMARY KEY,
                               product_id INT NOT NULL,
                               ordered_items_quantity INT NOT NULL,
                               ordered_items_unit_price DECIMAL(10,2) NOT NULL,
                               order_id INT NOT NULL,
                               FOREIGN KEY (product_id) REFERENCES product (product_id),
                               FOREIGN KEY (order_id) REFERENCES orders (order_id)
);