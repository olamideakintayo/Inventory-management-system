-- ================================================
-- DATABASE SCHEMA: Inventory Management System
-- ================================================

-- Drop in correct dependency order to avoid constraint conflicts
DROP TABLE IF EXISTS purchase_orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS warehouses;

-- ================================================
-- SUPPLIERS TABLE
-- ================================================
CREATE TABLE suppliers (
                           supplier_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           contact_information VARCHAR(255) NOT NULL
);

-- ================================================
-- WAREHOUSES TABLE
-- ================================================
CREATE TABLE warehouses (
                            warehouse_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            location VARCHAR(255) NOT NULL,
                            capacity INT NOT NULL
);

-- ================================================
-- PRODUCTS TABLE
-- ================================================
CREATE TABLE products (
                          product_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT NOT NULL,
                          re_order_threshold INT NOT NULL,
                          quantity_in_stock INT NOT NULL,
                          warehouse_id BIGINT,

                          CONSTRAINT fk_product_warehouse FOREIGN KEY (warehouse_id)
                              REFERENCES warehouses (warehouse_id)
                              ON DELETE SET NULL
);

-- ================================================
-- PURCHASE ORDERS TABLE
-- ================================================
CREATE TABLE purchase_orders (
                                 order_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 product_id BIGINT NOT NULL,
                                 supplier_id BIGINT NOT NULL,
                                 warehouse_id BIGINT NOT NULL,
                                 quantity_ordered INT NOT NULL,
                                 order_date DATE NOT NULL,
                                 expected_arrival_date DATE NOT NULL,

                                 CONSTRAINT fk_product FOREIGN KEY (product_id)
                                     REFERENCES products (product_id)
                                     ON DELETE CASCADE,

                                 CONSTRAINT fk_supplier FOREIGN KEY (supplier_id)
                                     REFERENCES suppliers (supplier_id)
                                     ON DELETE CASCADE,

                                 CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id)
                                     REFERENCES warehouses (warehouse_id)
                                     ON DELETE CASCADE
);
