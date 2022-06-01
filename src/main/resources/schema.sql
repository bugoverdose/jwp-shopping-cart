DROP TABLE IF EXISTS orders_detail;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    price     INTEGER      NOT NULL,
    image_url VARCHAR(255),
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE cart_item
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE orders
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE orders_detail
(
    id         BIGINT  NOT NULL AUTO_INCREMENT,
    orders_id  BIGINT  NOT NULL,
    product_id BIGINT  NOT NULL,
    quantity   INTEGER NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;
