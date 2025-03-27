CREATE DATABASE gsb_db_ecommerce;
USE gsb_db_ecommerce;

create table delivery_address
(
    delivery_address_id       int auto_increment
        primary key,
    delivery_address_city     varchar(100) not null,
    delivery_address_street   varchar(100) not null,
    delivery_address_zip_code int(5)       not null,
    delivery_address_country  varchar(100) not null
);

create table products
(
    product_id          int auto_increment
        primary key,
    product_name        varchar(100)   not null,
    product_description varchar(100)   not null,
    product_price       decimal(10, 2) not null,
    product_stock       int(100)       not null
);

create table roles
(
    role_id   int auto_increment
        primary key,
    role_name varchar(50) default 'medical-employee' not null
);

create table users
(
    user_id            int auto_increment
        primary key,
    user_name          varchar(100)                        not null,
    user_surname       varchar(100)                        not null,
    user_email         varchar(100)                        not null,
    user_password      varchar(100)                        not null,
    user_date_creation timestamp default CURRENT_TIMESTAMP not null,
    user_modified_at   timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    role_id            int                                 null,
    constraint user_email
        unique (user_email),
    constraint fk_role_id
        foreign key (role_id) references roles (role_id)
            on update cascade on delete set null
);

create table orders
(
    order_id            int auto_increment
        primary key,
    user_id             int                                   not null,
    order_status        varchar(50) default 'En attente'      not null,
    order_total_price   decimal(10, 2)                        not null,
    delivery_address_id int                                   null,
    order_created_at    timestamp   default CURRENT_TIMESTAMP not null,
    order_updated_at    timestamp   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint fk_delivery_address_id
        foreign key (delivery_address_id) references delivery_address (delivery_address_id)
            on delete set null,
    constraint fk_user_id
        foreign key (user_id) references users (user_id)
            on delete cascade
);

create table ordered_items
(
    ordered_items_id         int auto_increment
        primary key,
    product_id               int            not null,
    ordered_items_quantity   int            not null,
    ordered_items_unit_price decimal(10, 2) not null,
    order_id                 int            not null,
    constraint fk_order_id
        foreign key (order_id) references orders (order_id)
            on delete cascade,
    constraint fk_product_id
        foreign key (product_id) references products (product_id)
            on delete cascade
);

