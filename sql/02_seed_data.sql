-- ============================================
-- E-Commerce Database Seed Data
-- Run this after 01_schema.sql
-- Total rows: 80+
-- ============================================

USE ecommerce_db;

-- ============================================
-- Insert Categories (5 rows)
-- ============================================
INSERT INTO categories (name, description) VALUES
('Electronics', 'Electronic devices and accessories'),
('Clothing', 'Apparel and fashion items'),
('Books', 'Physical and digital books'),
('Home & Garden', 'Home improvement and gardening supplies'),
('Sports & Outdoors', 'Sports equipment and outdoor gear');

-- ============================================
-- Insert Users (10 rows)
-- ============================================
INSERT INTO users (email, password_hash, first_name, last_name, phone, role, is_active) VALUES
('john.doe@email.com', '$2a$10$abcdefghijklmnopqrstuvwxyz123456', 'John', 'Doe', '555-0101', 'CUSTOMER', TRUE),
('jane.smith@email.com', '$2a$10$bcdefghijklmnopqrstuvwxyz234567', 'Jane', 'Smith', '555-0102', 'CUSTOMER', TRUE),
('admin@ecommerce.com', '$2a$10$cdefghijklmnopqrstuvwxyz345678', 'Admin', 'User', '555-0100', 'ADMIN', TRUE),
('bob.jones@email.com', '$2a$10$defghijklmnopqrstuvwxyz456789', 'Bob', 'Jones', '555-0103', 'CUSTOMER', TRUE),
('alice.wilson@email.com', '$2a$10$efghijklmnopqrstuvwxyz567890', 'Alice', 'Wilson', '555-0104', 'CUSTOMER', TRUE),
('charlie.brown@email.com', '$2a$10$fghijklmnopqrstuvwxyz678901', 'Charlie', 'Brown', '555-0105', 'CUSTOMER', TRUE),
('diana.prince@email.com', '$2a$10$ghijklmnopqrstuvwxyz789012', 'Diana', 'Prince', '555-0106', 'CUSTOMER', TRUE),
('edward.stark@email.com', '$2a$10$hijklmnopqrstuvwxyz890123', 'Edward', 'Stark', '555-0107', 'CUSTOMER', TRUE),
('fiona.green@email.com', '$2a$10$ijklmnopqrstuvwxyz901234', 'Fiona', 'Green', '555-0108', 'CUSTOMER', TRUE),
('george.miller@email.com', '$2a$10$jklmnopqrstuvwxyz012345', 'George', 'Miller', '555-0109', 'CUSTOMER', TRUE);

-- ============================================
-- Insert Addresses (12 rows)
-- ============================================
INSERT INTO addresses (user_id, address_type, street_address, city, state, postal_code, country, is_default) VALUES
(1, 'SHIPPING', '123 Main St', 'New York', 'NY', '10001', 'USA', TRUE),
(1, 'BILLING', '123 Main St', 'New York', 'NY', '10001', 'USA', TRUE),
(2, 'SHIPPING', '456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA', TRUE),
(2, 'BILLING', '789 Pine Rd', 'Los Angeles', 'CA', '90002', 'USA', FALSE),
(4, 'SHIPPING', '321 Elm St', 'Chicago', 'IL', '60601', 'USA', TRUE),
(5, 'SHIPPING', '654 Maple Dr', 'Houston', 'TX', '77001', 'USA', TRUE),
(6, 'SHIPPING', '987 Cedar Ln', 'Phoenix', 'AZ', '85001', 'USA', TRUE),
(7, 'SHIPPING', '147 Birch Ct', 'Philadelphia', 'PA', '19101', 'USA', TRUE),
(8, 'SHIPPING', '258 Spruce Way', 'San Antonio', 'TX', '78201', 'USA', TRUE),
(9, 'SHIPPING', '369 Willow Blvd', 'San Diego', 'CA', '92101', 'USA', TRUE),
(10, 'SHIPPING', '741 Ash Pkwy', 'Dallas', 'TX', '75201', 'USA', TRUE),
(10, 'BILLING', '741 Ash Pkwy', 'Dallas', 'TX', '75201', 'USA', TRUE);

-- ============================================
-- Insert Products (20 rows)
-- ============================================
INSERT INTO products (category_id, name, description, price, stock, sku, is_active) VALUES
-- Electronics (5 products)
(1, 'Wireless Bluetooth Headphones', 'Premium noise-cancelling headphones with 30-hour battery', 149.99, 50, 'ELEC-HEAD-001', TRUE),
(1, 'Smart Watch Pro', 'Fitness tracker with heart rate monitor and GPS', 299.99, 35, 'ELEC-WATCH-001', TRUE),
(1, 'USB-C Hub 7-in-1', 'Multi-port adapter with HDMI, USB 3.0, and SD card reader', 39.99, 100, 'ELEC-HUB-001', TRUE),
(1, 'Wireless Charging Pad', 'Fast charging for Qi-enabled devices', 29.99, 75, 'ELEC-CHRG-001', TRUE),
(1, 'Portable Bluetooth Speaker', 'Waterproof speaker with 360-degree sound', 79.99, 60, 'ELEC-SPKR-001', TRUE),
-- Clothing (5 products)
(2, 'Classic Cotton T-Shirt', 'Comfortable everyday wear, available in multiple colors', 24.99, 200, 'CLTH-TSHRT-001', TRUE),
(2, 'Slim Fit Denim Jeans', 'Stretch denim with modern fit', 59.99, 120, 'CLTH-JEANS-001', TRUE),
(2, 'Hooded Sweatshirt', 'Warm fleece-lined hoodie', 44.99, 80, 'CLTH-HOOD-001', TRUE),
(2, 'Running Shorts', 'Lightweight athletic shorts with pockets', 29.99, 150, 'CLTH-SHRT-001', TRUE),
(2, 'Winter Jacket', 'Insulated water-resistant jacket', 129.99, 45, 'CLTH-JCKT-001', TRUE),
-- Books (4 products)
(3, 'Introduction to Algorithms', 'Comprehensive guide to computer science algorithms', 89.99, 30, 'BOOK-CS-001', TRUE),
(3, 'The Art of War', 'Classic strategy and philosophy text', 14.99, 100, 'BOOK-PHIL-001', TRUE),
(3, 'Clean Code', 'A handbook of agile software craftsmanship', 44.99, 55, 'BOOK-CS-002', TRUE),
(3, 'Sapiens: A Brief History', 'Human history from Stone Age to modern era', 24.99, 70, 'BOOK-HIST-001', TRUE),
-- Home & Garden (3 products)
(4, 'LED Desk Lamp', 'Adjustable brightness with USB charging port', 34.99, 90, 'HOME-LAMP-001', TRUE),
(4, 'Stainless Steel Cookware Set', '10-piece non-stick cookware', 199.99, 25, 'HOME-COOK-001', TRUE),
(4, 'Indoor Plant Starter Kit', 'Everything needed to grow herbs indoors', 39.99, 65, 'HOME-PLNT-001', TRUE),
-- Sports & Outdoors (3 products)
(5, 'Yoga Mat Premium', 'Extra thick non-slip exercise mat', 49.99, 110, 'SPRT-YOGA-001', TRUE),
(5, 'Camping Tent 4-Person', 'Weatherproof tent with easy setup', 159.99, 20, 'SPRT-TENT-001', TRUE),
(5, 'Water Bottle Insulated', '32oz stainless steel with temperature control', 29.99, 140, 'SPRT-BTTLE-001', TRUE);

-- ============================================
-- Insert Orders (8 rows)
-- ============================================
INSERT INTO orders (user_id, order_number, status, total_amount, shipping_address_id, billing_address_id, order_date) VALUES
(1, 'ORD-2026-0001', 'DELIVERED', 224.98, 1, 2, '2026-01-15 10:30:00'),
(2, 'ORD-2026-0002', 'SHIPPED', 89.99, 3, 4, '2026-01-18 14:20:00'),
(4, 'ORD-2026-0003', 'PAID', 379.98, 5, 5, '2026-01-20 09:15:00'),
(5, 'ORD-2026-0004', 'PENDING', 149.99, 6, 6, '2026-01-22 16:45:00'),
(1, 'ORD-2026-0005', 'PAID', 159.99, 1, 2, '2026-01-23 11:00:00'),
(6, 'ORD-2026-0006', 'SHIPPED', 74.98, 7, 7, '2026-01-24 13:30:00'),
(7, 'ORD-2026-0007', 'CANCELLED', 299.99, 8, 8, '2026-01-25 08:20:00'),
(9, 'ORD-2026-0008', 'PENDING', 194.97, 10, 10, '2026-01-26 10:00:00');

-- ============================================
-- Insert Order Items (20 rows)
-- ============================================
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal) VALUES
-- Order 1 (2 items)
(1, 1, 1, 149.99, 149.99),
(1, 4, 2, 29.99, 59.98),
-- Order 2 (1 item)
(2, 11, 1, 89.99, 89.99),
-- Order 3 (3 items)
(3, 2, 1, 299.99, 299.99),
(3, 6, 2, 24.99, 49.98),
(3, 9, 1, 29.99, 29.99),
-- Order 4 (1 item)
(4, 1, 1, 149.99, 149.99),
-- Order 5 (1 item)
(5, 19, 1, 159.99, 159.99),
-- Order 6 (3 items)
(6, 6, 1, 24.99, 24.99),
(6, 9, 1, 29.99, 29.99),
(6, 14, 1, 24.99, 24.99),
-- Order 7 (1 item)
(7, 2, 1, 299.99, 299.99),
-- Order 8 (5 items)
(8, 18, 1, 49.99, 49.99),
(8, 3, 2, 39.99, 79.98),
(8, 15, 1, 34.99, 34.99),
(8, 12, 1, 14.99, 14.99),
(8, 20, 1, 29.99, 29.99);

-- ============================================
-- Insert Payments (7 rows - some orders pending)
-- ============================================
INSERT INTO payments (order_id, payment_method, transaction_id, amount, payment_status, payment_date) VALUES
(1, 'CREDIT_CARD', 'TXN-CC-20260115-001', 224.98, 'COMPLETED', '2026-01-15 10:31:00'),
(2, 'PAYPAL', 'TXN-PP-20260118-001', 89.99, 'COMPLETED', '2026-01-18 14:22:00'),
(3, 'DEBIT_CARD', 'TXN-DC-20260120-001', 379.98, 'COMPLETED', '2026-01-20 09:17:00'),
(5, 'CREDIT_CARD', 'TXN-CC-20260123-001', 159.99, 'COMPLETED', '2026-01-23 11:02:00'),
(6, 'BANK_TRANSFER', 'TXN-BT-20260124-001', 74.98, 'COMPLETED', '2026-01-24 13:35:00'),
(7, 'CREDIT_CARD', 'TXN-CC-20260125-001', 299.99, 'REFUNDED', '2026-01-25 08:25:00'),
(4, 'PAYPAL', 'TXN-PP-20260122-001', 149.99, 'PENDING', '2026-01-22 16:47:00');

-- ============================================
-- Insert Inventory Logs (10 rows)
-- ============================================
INSERT INTO inventory_logs (product_id, change_type, quantity_change, previous_stock, new_stock, notes) VALUES
(1, 'RESTOCK', 50, 0, 50, 'Initial inventory'),
(1, 'SALE', -2, 50, 48, 'Sold via orders'),
(2, 'RESTOCK', 40, 0, 40, 'Initial inventory'),
(2, 'SALE', -2, 40, 38, 'Sold via orders'),
(3, 'RESTOCK', 100, 0, 100, 'Initial inventory'),
(3, 'SALE', -2, 100, 98, 'Sold via order 8'),
(6, 'RESTOCK', 200, 0, 200, 'Initial inventory'),
(6, 'SALE', -3, 200, 197, 'Sold via multiple orders'),
(19, 'RESTOCK', 25, 0, 25, 'Initial inventory'),
(19, 'SALE', -1, 25, 24, 'Sold via order 5');

-- ============================================
-- Row Count Summary
-- ============================================
-- categories: 5 rows
-- users: 10 rows
-- addresses: 12 rows
-- products: 20 rows
-- orders: 8 rows
-- order_items: 20 rows
-- payments: 7 rows
-- inventory_logs: 10 rows
-- TOTAL: 92 rows
-- ============================================

-- Verification queries
SELECT 'Categories' AS table_name, COUNT(*) AS row_count FROM categories
UNION ALL
SELECT 'Users', COUNT(*) FROM users
UNION ALL
SELECT 'Addresses', COUNT(*) FROM addresses
UNION ALL
SELECT 'Products', COUNT(*) FROM products
UNION ALL
SELECT 'Orders', COUNT(*) FROM orders
UNION ALL
SELECT 'Order Items', COUNT(*) FROM order_items
UNION ALL
SELECT 'Payments', COUNT(*) FROM payments
UNION ALL
SELECT 'Inventory Logs', COUNT(*) FROM inventory_logs
UNION ALL
SELECT 'TOTAL', 
    (SELECT COUNT(*) FROM categories) +
    (SELECT COUNT(*) FROM users) +
    (SELECT COUNT(*) FROM addresses) +
    (SELECT COUNT(*) FROM products) +
    (SELECT COUNT(*) FROM orders) +
    (SELECT COUNT(*) FROM order_items) +
    (SELECT COUNT(*) FROM payments) +
    (SELECT COUNT(*) FROM inventory_logs);
