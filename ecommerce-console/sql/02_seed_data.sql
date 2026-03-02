-- =============================================================
-- E-Commerce Seed / Test Data
-- CSCE 548 - Project 4
-- Run AFTER 01_schema.sql
-- =============================================================

USE ecommerce_db;

-- =============================================================
-- Categories (5 rows)
-- =============================================================
INSERT INTO categories (name, description) VALUES
('Electronics',   'Computers, phones, tablets, and accessories'),
('Clothing',      'Men''s, women''s, and children''s apparel'),
('Home & Garden', 'Furniture, decor, tools, and outdoor equipment'),
('Books',         'Fiction, non-fiction, textbooks, and e-books'),
('Sports',        'Athletic equipment, activewear, and outdoor gear');

-- =============================================================
-- Users (10 rows – 1 admin + 9 customers)
-- Passwords are SHA-256 hashes of "password123" for demo purposes
-- =============================================================
INSERT INTO users (email, username, password_hash, first_name, last_name, phone, role, is_active) VALUES
('admin@ecommerce.com',   's_a_3_7', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'System', 'Admin',    '555-0100', 'ADMIN',    TRUE),
('alice@example.com',     'a_j_5_2', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Alice',  'Johnson',  '555-0101', 'CUSTOMER', TRUE),
('bob@example.com',       'b_s_8_1', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Bob',    'Smith',    '555-0102', 'CUSTOMER', TRUE),
('carol@example.com',     'c_w_4_6', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Carol',  'Williams', '555-0103', 'CUSTOMER', TRUE),
('dave@example.com',      'd_b_2_9', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Dave',   'Brown',    '555-0104', 'CUSTOMER', TRUE),
('eve@example.com',       'e_j_7_3', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Eve',    'Jones',    '555-0105', 'CUSTOMER', TRUE),
('frank@example.com',     'f_g_1_5', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Frank',  'Garcia',   '555-0106', 'CUSTOMER', TRUE),
('grace@example.com',     'g_m_6_4', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Grace',  'Martinez', '555-0107', 'CUSTOMER', TRUE),
('henry@example.com',     'h_d_9_2', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Henry',  'Davis',    '555-0108', 'CUSTOMER', TRUE),
('iris@example.com',      'i_w_3_8', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Iris',   'Wilson',   '555-0109', 'CUSTOMER', FALSE);

-- =============================================================
-- Addresses (12 rows)
-- =============================================================
INSERT INTO addresses (user_id, address_line1, address_line2, city, state, postal_code, country, address_type) VALUES
(2, '123 Maple St',    NULL,       'Austin',      'TX', '78701', 'US', 'BOTH'),
(2, '456 Oak Ave',     'Apt 2B',   'Austin',      'TX', '78702', 'US', 'BILLING'),
(3, '789 Pine Rd',     NULL,       'Dallas',      'TX', '75201', 'US', 'BOTH'),
(4, '321 Elm Dr',      NULL,       'Houston',     'TX', '77001', 'US', 'SHIPPING'),
(5, '654 Cedar Ln',    'Suite 10', 'San Antonio', 'TX', '78201', 'US', 'BOTH'),
(6, '987 Birch Blvd',  NULL,       'Fort Worth',  'TX', '76101', 'US', 'BOTH'),
(7, '147 Spruce Ct',   NULL,       'El Paso',     'TX', '79901', 'US', 'BOTH'),
(8, '258 Willow Way',  'Apt 3',    'Arlington',   'TX', '76010', 'US', 'BOTH'),
(9, '369 Walnut Pl',   NULL,       'Corpus Christi','TX','78401','US', 'BOTH'),
(3, '185 River Rd',    NULL,       'Plano',       'TX', '75023', 'US', 'BILLING'),
(4, '720 Sunset Blvd', NULL,       'Irving',      'TX', '75061', 'US', 'SHIPPING'),
(5, '330 Park Ave',    'Floor 2',  'Lubbock',     'TX', '79401', 'US', 'BILLING');

-- =============================================================
-- Products (20 rows across 5 categories)
-- =============================================================
INSERT INTO products (category_id, name, description, price, stock, sku, is_active) VALUES
-- Electronics (cat 1)
(1, 'Laptop Pro 15',          '15-inch laptop with Intel i7, 16GB RAM, 512GB SSD',   1299.99, 25, 'ELEC-LP15-001', TRUE),
(1, 'Wireless Headphones X',  'Noise-cancelling over-ear Bluetooth headphones',        149.99, 80, 'ELEC-WH-X002',  TRUE),
(1, 'Smartphone Ultra 12',    '6.7-inch OLED, 256GB, quad camera',                    899.99, 40, 'ELEC-SU12-003', TRUE),
(1, 'USB-C Hub 7-Port',       'Multiport hub with HDMI, USB-A, SD card reader',        49.99, 150,'ELEC-HUB-004',  TRUE),
-- Clothing (cat 2)
(2, 'Men''s Running Jacket',  'Lightweight wind-resistant jacket, sizes S-XXL',        79.99, 60, 'CLTH-MRJ-005',  TRUE),
(2, 'Women''s Yoga Pants',    'High-waist moisture-wicking leggings',                  59.99, 90, 'CLTH-WYP-006',  TRUE),
(2, 'Classic Polo Shirt',     'Pique cotton polo, 8 colors available',                 34.99, 120,'CLTH-CPS-007',  TRUE),
(2, 'Denim Jacket',           'Unisex distressed denim, relaxed fit',                  89.99, 45, 'CLTH-DJ-008',   TRUE),
-- Home & Garden (cat 3)
(3, 'Ceramic Coffee Mug Set', 'Set of 4 handmade mugs, 14 oz each',                   29.99, 200,'HMGD-CCM-009',  TRUE),
(3, 'Robot Vacuum Cleaner',   'Smart mapping, 2500 Pa suction, works with Alexa',     299.99, 30, 'HMGD-RVC-010',  TRUE),
(3, 'Garden Tool Kit 10pc',   'Stainless steel tools with ergonomic rubber grips',    54.99, 70, 'HMGD-GTK-011',  TRUE),
(3, 'LED Desk Lamp',          'Adjustable arm, USB charging port, 5 brightness levels',39.99, 95, 'HMGD-LDL-012',  TRUE),
-- Books (cat 4)
(4, 'Clean Code',             'A handbook of agile software craftsmanship – R. Martin', 39.99, 55, 'BOOK-CC-013',   TRUE),
(4, 'The Pragmatic Programmer','From journeyman to master – Hunt & Thomas',             44.99, 48, 'BOOK-PP-014',   TRUE),
(4, 'Database Internals',     'A deep-dive into how distributed data systems work',    52.99, 32, 'BOOK-DI-015',   TRUE),
(4, 'Algorithms 4th Ed.',     'Sedgewick & Wayne – Java implementation',               74.99, 20, 'BOOK-ALG-016',  TRUE),
-- Sports (cat 5)
(5, 'Yoga Mat Premium',       'Extra thick 6mm non-slip mat with carrying strap',      29.99, 180,'SPRT-YMP-017',  TRUE),
(5, 'Adjustable Dumbbells',   'Set of 2, 5-25 lbs each with quick-lock system',       199.99, 22, 'SPRT-ADB-018',  TRUE),
(5, 'Cycling Helmet',         'MIPS-certified, adjustable fit, 11 vents',              89.99, 55, 'SPRT-CH-019',   TRUE),
(5, 'Hydration Backpack',     '2L reservoir, 18 pockets, ultralight frame',            69.99, 40, 'SPRT-HBP-020',  TRUE);

-- =============================================================
-- Orders (8 rows)
-- =============================================================
INSERT INTO orders (user_id, order_number, status, total_amount, shipping_address_id, billing_address_id, notes) VALUES
(2, 'ORD-2026-0001', 'DELIVERED', 1449.98, 1,  2,  'Leave at front door'),
(3, 'ORD-2026-0002', 'SHIPPED',   299.99,  3,  3,  NULL),
(4, 'ORD-2026-0003', 'PAID',      239.97,  4,  4,  'Gift wrap please'),
(5, 'ORD-2026-0004', 'PENDING',   129.98,  5,  5,  NULL),
(6, 'ORD-2026-0005', 'DELIVERED', 74.98,   6,  6,  NULL),
(7, 'ORD-2026-0006', 'CANCELLED', 199.99,  7,  7,  'Customer cancelled'),
(8, 'ORD-2026-0007', 'PENDING',   94.98,   8,  8,  NULL),
(2, 'ORD-2026-0008', 'PAID',      399.98,  1,  2,  'Second order from Alice');

-- =============================================================
-- Order Items (20 rows)
-- =============================================================
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
-- Order 1: Laptop + Headphones
(1, 1, 1, 1299.99),
(1, 2, 1,  149.99),
-- Order 2: Robot Vacuum
(2, 10, 1, 299.99),
-- Order 3: Cycling Helmet + Yoga Pants + Polo
(3, 19, 1,  89.99),
(3,  6, 2,  59.99),
(3,  7, 1,  34.99),
-- Order 4: Running Jacket + Yoga Mat
(4,  5, 1,  79.99),
(4, 17, 1,  29.99),
-- Order 5: Clean Code + Pragmatic Programmer
(5, 13, 1,  39.99),
(5, 14, 1,  39.99),  -- discounted price
-- Order 6: Adjustable Dumbbells (cancelled)
(6, 18, 1, 199.99),
-- Order 7: Mug Set + LED Lamp
(7,  9, 1,  29.99),
(7, 12, 1,  39.99),
(7, 17, 1,  29.99),  -- extra yoga mat
-- Order 8: USB-C Hub + Headphones + Desk Lamp
(8,  4, 2,  49.99),
(8,  2, 1, 149.99),
(8, 12, 1,  39.99),
-- Additional rows to hit 92+ total rows
(1, 17, 2,  29.99),
(3, 12, 1,  39.99),
(8,  7, 2,  34.99);

-- =============================================================
-- Payments (7 rows)
-- =============================================================
INSERT INTO payments (order_id, amount, payment_method, payment_status, transaction_id) VALUES
(1, 1449.98, 'CREDIT_CARD',   'COMPLETED', 'TXN-CC-2026-001'),
(2,  299.99, 'PAYPAL',        'COMPLETED', 'TXN-PP-2026-002'),
(3,  239.97, 'DEBIT_CARD',    'COMPLETED', 'TXN-DC-2026-003'),
(4,  129.98, 'CREDIT_CARD',   'PENDING',   'TXN-CC-2026-004'),
(5,   74.98, 'BANK_TRANSFER', 'COMPLETED', 'TXN-BT-2026-005'),
(6,  199.99, 'CREDIT_CARD',   'REFUNDED',  'TXN-CC-2026-006'),
(8,  399.98, 'PAYPAL',        'COMPLETED', 'TXN-PP-2026-007');

-- =============================================================
-- Inventory Logs (10 rows)
-- =============================================================
INSERT INTO inventory_logs (product_id, change_type, quantity_change, notes) VALUES
( 1, 'RESTOCK',    50, 'Initial stock'),
( 2, 'RESTOCK',   100, 'Initial stock'),
( 3, 'RESTOCK',    60, 'Initial stock'),
(10, 'RESTOCK',    40, 'Initial stock'),
(18, 'RESTOCK',    30, 'Initial stock'),
( 1, 'SALE',       -1, 'Order ORD-2026-0001'),
( 2, 'SALE',       -2, 'Orders ORD-2026-0001 and ORD-2026-0008'),
(10, 'SALE',       -1, 'Order ORD-2026-0002'),
(18, 'SALE',       -1, 'Order ORD-2026-0006 (reversed due to cancel)'),
(18, 'RETURN',     +1, 'Order ORD-2026-0006 cancelled and restocked');

-- =============================================================
-- Verification queries
-- =============================================================
SELECT
    (SELECT COUNT(*) FROM categories)     AS categories,
    (SELECT COUNT(*) FROM users)          AS users,
    (SELECT COUNT(*) FROM addresses)      AS addresses,
    (SELECT COUNT(*) FROM products)       AS products,
    (SELECT COUNT(*) FROM orders)         AS orders,
    (SELECT COUNT(*) FROM order_items)    AS order_items,
    (SELECT COUNT(*) FROM payments)       AS payments,
    (SELECT COUNT(*) FROM inventory_logs) AS inventory_logs;
-- Expected: 5 | 10 | 12 | 20 | 8 | 20 | 7 | 10  → Total = 92 rows
