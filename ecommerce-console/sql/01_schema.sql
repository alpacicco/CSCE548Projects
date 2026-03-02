-- =============================================================
-- E-Commerce Database Schema
-- CSCE 548 - Project 4
-- MySQL 8.0+
-- =============================================================

-- Drop and recreate the database
DROP DATABASE IF EXISTS ecommerce_db;
CREATE DATABASE ecommerce_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE ecommerce_db;

-- =============================================================
-- TABLE: categories
-- =============================================================
CREATE TABLE categories (
    category_id   INT           NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100)  NOT NULL,
    description   TEXT,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (category_id),
    UNIQUE KEY uq_categories_name (name)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: users
-- =============================================================
CREATE TABLE users (
    user_id       INT           NOT NULL AUTO_INCREMENT,
    email         VARCHAR(150)  NOT NULL,
    password_hash VARCHAR(255)  NOT NULL,
    first_name    VARCHAR(80)   NOT NULL,
    last_name     VARCHAR(80)   NOT NULL,
    phone         VARCHAR(30),
    role          ENUM('CUSTOMER','ADMIN') NOT NULL DEFAULT 'CUSTOMER',
    is_active     BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE KEY uq_users_email (email),
    INDEX idx_users_role (role)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: addresses
-- Shipping / billing addresses linked to a user
-- =============================================================
CREATE TABLE addresses (
    address_id    INT           NOT NULL AUTO_INCREMENT,
    user_id       INT           NOT NULL,
    address_line1 VARCHAR(200)  NOT NULL,
    address_line2 VARCHAR(200),
    city          VARCHAR(100)  NOT NULL,
    state         VARCHAR(100)  NOT NULL,
    postal_code   VARCHAR(20)   NOT NULL,
    country       VARCHAR(100)  NOT NULL DEFAULT 'US',
    address_type  ENUM('SHIPPING','BILLING','BOTH') NOT NULL DEFAULT 'BOTH',
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (address_id),
    CONSTRAINT fk_addresses_user FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_addresses_user (user_id)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: products
-- =============================================================
CREATE TABLE products (
    product_id    INT             NOT NULL AUTO_INCREMENT,
    category_id   INT             NOT NULL,
    name          VARCHAR(200)    NOT NULL,
    description   TEXT,
    price         DECIMAL(10,2)   NOT NULL,
    stock         INT             NOT NULL DEFAULT 0,
    sku           VARCHAR(80)     NOT NULL,
    is_active     BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (product_id),
    UNIQUE KEY uq_products_sku (sku),
    CONSTRAINT fk_products_category FOREIGN KEY (category_id)
        REFERENCES categories (category_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_products_price   CHECK (price >= 0),
    CONSTRAINT chk_products_stock   CHECK (stock >= 0),
    INDEX idx_products_category (category_id),
    INDEX idx_products_active (is_active)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: orders
-- =============================================================
CREATE TABLE orders (
    order_id             INT             NOT NULL AUTO_INCREMENT,
    user_id              INT             NOT NULL,
    order_number         VARCHAR(50)     NOT NULL,
    status               ENUM('PENDING','PAID','SHIPPED','DELIVERED','CANCELLED')
                                         NOT NULL DEFAULT 'PENDING',
    total_amount         DECIMAL(12,2)   NOT NULL DEFAULT 0.00,
    shipping_address_id  INT,
    billing_address_id   INT,
    order_date           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    shipped_date         TIMESTAMP,
    delivered_date       TIMESTAMP,
    notes                TEXT,
    created_at           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (order_id),
    UNIQUE KEY uq_orders_number (order_number),
    CONSTRAINT fk_orders_user     FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_orders_ship_addr FOREIGN KEY (shipping_address_id)
        REFERENCES addresses (address_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_orders_bill_addr FOREIGN KEY (billing_address_id)
        REFERENCES addresses (address_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT chk_orders_total   CHECK (total_amount >= 0),
    INDEX idx_orders_user   (user_id),
    INDEX idx_orders_status (status)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: order_items
-- =============================================================
CREATE TABLE order_items (
    order_item_id INT             NOT NULL AUTO_INCREMENT,
    order_id      INT             NOT NULL,
    product_id    INT             NOT NULL,
    quantity      INT             NOT NULL,
    unit_price    DECIMAL(10,2)   NOT NULL,
    subtotal      DECIMAL(12,2)   GENERATED ALWAYS AS (quantity * unit_price) STORED,
    created_at    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (order_item_id),
    CONSTRAINT fk_order_items_order   FOREIGN KEY (order_id)
        REFERENCES orders (order_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id)
        REFERENCES products (product_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_order_items_qty    CHECK (quantity > 0),
    CONSTRAINT chk_order_items_price  CHECK (unit_price >= 0),
    INDEX idx_order_items_order   (order_id),
    INDEX idx_order_items_product (product_id)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: payments
-- =============================================================
CREATE TABLE payments (
    payment_id       INT             NOT NULL AUTO_INCREMENT,
    order_id         INT             NOT NULL,
    amount           DECIMAL(12,2)   NOT NULL,
    payment_method   ENUM('CREDIT_CARD','DEBIT_CARD','PAYPAL','BANK_TRANSFER','CASH')
                                      NOT NULL DEFAULT 'CREDIT_CARD',
    payment_status   ENUM('PENDING','COMPLETED','FAILED','REFUNDED')
                                      NOT NULL DEFAULT 'PENDING',
    transaction_id   VARCHAR(100),
    payment_date     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (payment_id),
    UNIQUE KEY uq_payments_transaction (transaction_id),
    CONSTRAINT fk_payments_order FOREIGN KEY (order_id)
        REFERENCES orders (order_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_payments_amount CHECK (amount > 0),
    INDEX idx_payments_order  (order_id),
    INDEX idx_payments_status (payment_status)
) ENGINE=InnoDB;

-- =============================================================
-- TABLE: inventory_logs
-- Audit trail for stock changes
-- =============================================================
CREATE TABLE inventory_logs (
    log_id        INT          NOT NULL AUTO_INCREMENT,
    product_id    INT          NOT NULL,
    change_type   ENUM('RESTOCK','SALE','ADJUSTMENT','RETURN') NOT NULL,
    quantity_change INT        NOT NULL,
    notes         VARCHAR(255),
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (log_id),
    CONSTRAINT fk_inv_logs_product FOREIGN KEY (product_id)
        REFERENCES products (product_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_inv_logs_product (product_id)
) ENGINE=InnoDB;

-- =============================================================
-- Summary: 8 tables, 9 foreign keys, multiple constraints
-- =============================================================
