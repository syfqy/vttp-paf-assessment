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