# E-Commerce Console Application - How to Run

## Quick Start Guide

### 1️⃣ Database Setup (5 minutes)

```bash
# Start MySQL (Windows)
net start MySQL84

# Create database and schema
mysql -u root -p < sql/01_schema.sql

$env:Path += ";C:\Program Files\MySQL\MySQL Server 8.4\bin"
mysql -u root -e "USE ecommerce_db; SELECT 'Total Rows' as Metric, (SELECT COUNT(*) FROM categories) + (SELECT COUNT(*) FROM users) + (SELECT COUNT(*) FROM addresses) + (SELECT COUNT(*) FROM products) + (SELECT COUNT(*) FROM orders) + (SELECT COUNT(*) FROM order_items) + (SELECT COUNT(*) FROM payments) + (SELECT COUNT(*) FROM inventory_logs) as Count;"# Load test data (92+ rows)
mysql -u root -p < sql/02_seed_data.sql

# Verify data loaded
mysql -u root -p ecommerce_db
```

In MySQL prompt:
```sql
-- Check row counts
SELECT 
    (SELECT COUNT(*) FROM categories) as categories,
    (SELECT COUNT(*) FROM users) as users,
    (SELECT COUNT(*) FROM products) as products,
    (SELECT COUNT(*) FROM orders) as orders,
    (SELECT COUNT(*) FROM order_items) as order_items,
    (SELECT COUNT(*) FROM payments) as payments,
    (SELECT COUNT(*) FROM addresses) as addresses,
    (SELECT COUNT(*) FROM inventory_logs) as inventory_logs;

-- Should show: 5, 10, 20, 8, 20, 7, 12, 10 respectively (Total: 92 rows)
```

### 2️⃣ Configure Application (1 minute)

```bash
cd ecommerce-console

# Copy environment template
cp .env.example .env

# Edit .env with your MySQL password
# (Use notepad, VS Code, or any text editor)
notepad .env
```

Edit `.env`:
```properties
DB_HOST=localhost
DB_PORT=3306
DB_NAME=ecommerce_db
DB_USER=root
DB_PASSWORD=YOUR_MYSQL_ROOT_PASSWORD_HERE
```

### 3️⃣ Build & Run (2 minutes)

```bash
# Install dependencies and compile
mvn clean install

# Run the application
mvn exec:java
```

**Expected Output:**
```
Starting E-Commerce Console Application...

Testing database connection... ✓ Connected successfully!

═══════════════════════════════════════════
   E-COMMERCE CONSOLE APPLICATION
═══════════════════════════════════════════

┌──────────────────────────────────────────┐
│              MAIN MENU                   │
├──────────────────────────────────────────┤
│  1. List All Products                    │
│  2. Search Products by Category          │
...
```

## Alternative: Run from JAR

```bash
# Build JAR file
mvn clean package

# Run JAR
java -jar target/ecommerce-console-1.0-SNAPSHOT.jar
```

## 📋 Pre-flight Checklist

Before running, verify:

- ✅ Java 17+ installed: `java -version`
- ✅ Maven installed: `mvn -version`
- ✅ MySQL 8+ running: `mysql --version`
- ✅ Database created: `mysql -u root -p -e "SHOW DATABASES;" | grep ecommerce_db`
- ✅ `.env` file exists with correct password
- ✅ In correct directory: `ls pom.xml` (should exist)

## 🎮 Testing the Application

### Test Scenario 1: View Data
1. Select option `1` - List All Products
2. Select option `8` - List All Categories
3. Select option `9` - List All Users

### Test Scenario 2: Search
1. Select option `2` - Search Products by Category
2. Enter category ID `1` (Electronics)
3. View filtered results

### Test Scenario 3: Create User
1. Select option `3` - Create New User
2. Enter:
   - Email: `test@example.com`
   - First Name: `Test`
   - Last Name: `User`
   - Phone: `555-9999`
   - Role: `CUSTOMER` (or just press Enter)
3. Note the generated User ID

### Test Scenario 4: Place Order
1. Select option `4` - Place Order
2. Enter user ID `1` (John Doe)
3. Add products:
   - Product ID `1` (Headphones), Quantity `1`
   - Product ID `3` (USB Hub), Quantity `2`
   - Enter `0` to finish
4. Verify order total calculated correctly

### Test Scenario 5: View Order History
1. Select option `5` - View Orders for User
2. Enter user ID `1`
3. See list of orders with status and totals

### Test Scenario 6: View Order Details (JOIN)
1. Select option `10` - View Order Details
2. Enter order ID `1`
3. See customer info + order items with product names (demonstrates JOIN query)

### Test Scenario 7: Update Product
1. Select option `6` - Update Product Price/Stock
2. Enter product ID `1`
3. Update price to `159.99` or stock to `45`
4. Verify update successful

### Test Scenario 8: Delete Product (FK Constraint)
1. Select option `7` - Delete Product
2. Try to delete product ID `1` (has order items)
3. Observe foreign key constraint error
4. Try to delete product ID `20` (no orders)
5. Confirm deletion successful

## 🖼️ Screenshots to Capture

### For Instructor Submission

#### Screenshot 1: ER Diagram
- MySQL Workbench → Database → Reverse Engineer
- Select `ecommerce_db`
- Capture diagram showing 8 tables + relationships

#### Screenshot 2: Row Count Verification
```sql
SELECT 'Total Rows' as Metric, 
       (SELECT COUNT(*) FROM categories) +
       (SELECT COUNT(*) FROM users) +
       (SELECT COUNT(*) FROM addresses) +
       (SELECT COUNT(*) FROM products) +
       (SELECT COUNT(*) FROM orders) +
       (SELECT COUNT(*) FROM order_items) +
       (SELECT COUNT(*) FROM payments) +
       (SELECT COUNT(*) FROM inventory_logs) as Count;
```
Should show: **Total Rows: 92**

#### Screenshot 3: Console - List All Products
- Shows formatted table with 20 products

#### Screenshot 4: Console - View Order Details
- Shows order with JOIN query results (customer + items)

#### Screenshot 5: Console - Create New User
- Shows success message with generated ID

#### Screenshot 6: Console - Place Order
- Shows order workflow with products added

## 🔧 Troubleshooting

### Issue: "mysql command not found"
**Windows:**
```bash
# Add MySQL to PATH temporarily
set PATH=%PATH%;C:\Program Files\MySQL\MySQL Server 8.4\bin

# Then retry
mysql --version
```

### Issue: "Access denied for user 'root'"
**Solution:** Check password in `.env` matches your MySQL root password
```bash
# Test connection manually
mysql -u root -p
# Enter password when prompted
```

### Issue: "Database ecommerce_db does not exist"
**Solution:** Run schema script first
```bash
mysql -u root -p < sql/01_schema.sql
```

### Issue: "Could not find or load main class"
**Solution:** Rebuild project
```bash
mvn clean compile
mvn exec:java
```

### Issue: "Failed to connect to database"
**Solution:** Verify MySQL is running
```bash
# Windows
net start MySQL84

# Check status
mysqladmin -u root -p status
```

## 📊 Sample Queries for Testing

### Test Foreign Keys
```sql
-- Try to insert product with invalid category (should fail)
INSERT INTO products (category_id, name, price, stock, sku)
VALUES (999, 'Test', 10.00, 5, 'TEST-001');

-- Try to delete category with products (should fail)
DELETE FROM categories WHERE category_id = 1;
```

### Test Constraints
```sql
-- Try negative price (should fail - CHECK constraint)
INSERT INTO products (category_id, name, price, stock, sku)
VALUES (1, 'Bad Product', -10.00, 5, 'BAD-001');

-- Try duplicate email (should fail - UNIQUE constraint)
INSERT INTO users (email, password_hash, first_name, last_name, role)
VALUES ('john.doe@email.com', 'hash', 'Duplicate', 'User', 'CUSTOMER');
```

### Test Join Queries
```sql
-- Products by category with category name
SELECT p.name, p.price, c.name as category
FROM products p
JOIN categories c ON p.category_id = c.category_id
ORDER BY c.name, p.name;

-- Order details with customer and items
SELECT 
    o.order_number,
    CONCAT(u.first_name, ' ', u.last_name) as customer,
    o.status,
    p.name as product,
    oi.quantity,
    oi.subtotal
FROM orders o
JOIN users u ON o.user_id = u.user_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
WHERE o.order_id = 1;
```

## ⏱️ Expected Timing

- Database setup: ~5 minutes
- Application configuration: ~1 minute
- Build & first run: ~2 minutes
- Testing features: ~10 minutes
- **Total: ~20 minutes**

## 🎯 Success Criteria

You're ready to demo when you can:

1. ✅ Show ER diagram with 8 tables and 9 FK relationships
2. ✅ Prove 50+ rows exist (we have 92)
3. ✅ List all products in formatted console output
4. ✅ Search products by category
5. ✅ Create a new user
6. ✅ Place an order with multiple items
7. ✅ View order details (JOIN query)
8. ✅ Update product price/stock
9. ✅ Demonstrate FK constraint (delete fails appropriately)
10. ✅ Show CHECK constraint (negative price rejected)

---

**Need Help?** Check README.md for detailed technical documentation.
