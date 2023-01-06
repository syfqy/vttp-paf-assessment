DROP SCHEMA IF EXISTS eshop;
CREATE SCHEMA eshop;

USE eshop;

DROP TABLE IF EXISTS customers;
CREATE TABLE customers(

    name VARCHAR(32) NOT NULL,
    address VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,

    PRIMARY KEY (name)
    
);

INSERT INTO customers 
VALUES 
    ('fred', '201 Cobblestone Lane', 'fredflintstone@bedrock.com'),
    ('sherlock', '221B Baker Street, London', 'sherlock@consultingdetective.org'),
    ('spongebob', '124 Conch Street, Bikini Bottom', 'spongebob@yahoo.com'),
    ('jessica', '698 Candlewood Land, Cabot Cove', 'fletcher@gmail.com'),
    ('dursley', '4 Privet Drive, Little Whinging, Surrey', 'dursley@gmail.com')
;

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    order_id VARCHAR(32) NOT NULL,
    customer_name VARCHAR(32) NOT NULL,
    order_date DATE NOT NULL,

    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_name) REFERENCES customers (name)
);

DROP TABLE IF EXISTS line_items;
CREATE TABLE line_items (
    order_id VARCHAR(32) NOT NULL,
    item VARCHAR(256) NOT NULL,
    quantity INT NOT NULL,

    PRIMARY KEY (order_id, item),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status (
    order_id VARCHAR(32) NOT NULL,
    delivery_id VARCHAR(32),
    status VARCHAR(128),
    status_update TIMESTAMP,

    PRIMARY KEY (order_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

