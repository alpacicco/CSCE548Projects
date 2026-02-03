# E-Commerce Console Application

A professional-quality console-based e-commerce system built with Java 17+ and MySQL 8+, featuring a complete relational database schema, comprehensive DAO layer, and interactive CLI interface.

## 🏗️ Project Structure

```
ecommerce-console/
├── src/main/java/com/ecommerce/
│   ├── model/          # POJOs (Category, User, Product, Order, OrderItem, Address, Payment)
│   ├── dao/            # DAO interfaces and implementations
│   ├── db/             # Database connection utility
│   ├── ui/             # Console user interface
│   └── Main.java       # Application entry point
├── sql/
│   ├── 01_schema.sql   # Database schema with constraints
│   └── 02_seed_data.sql # Test data (92+ rows)
├── pom.xml             # Maven configuration
├── .env.example        # Environment configuration template
└── README.md           # This file
```

## 📊 Database Schema

### Tables (8 total)
- **categories** - Product categories
- **users** - Customer and admin accounts
- **addresses** - Shipping/billing addresses (FK to users)
- **products** - Product catalog (FK to categories)
- **orders** - Customer orders (FK to users, addresses)
- **order_items** - Order line items (FK to orders, products)
- **payments** - Payment records (FK to orders)
- **inventory_logs** - Inventory change audit trail (FK to products)

### Foreign Key Relationships (9 total)
1. `addresses.user_id` → `users.user_id`
2. `products.category_id` → `categories.category_id`
3. `orders.user_id` → `users.user_id`
4. `orders.shipping_address_id` → `addresses.address_id`
5. `orders.billing_address_id` → `addresses.address_id`
6. `order_items.order_id` → `orders.order_id`
7. `order_items.product_id` → `products.product_id`
8. `payments.order_id` → `orders.order_id`
9. `inventory_logs.product_id` → `products.product_id`

### Constraints
- Primary keys on all tables
- Foreign keys with appropriate CASCADE/RESTRICT rules
- UNIQUE constraints (user email, product SKU, order number, transaction ID)
- CHECK constraints (price > 0, stock >= 0, quantity > 0)
- NOT NULL constraints on required fields
- ENUM constraints for status fields
- Indexes on frequently queried columns

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

### Step 1: Clone Repository
```bash
git clone https://github.com/alpacicco/CSCE548Projects.git
cd CSCE548Projects/ecommerce-console
```

### Step 2: Set Up Database

1. **Start MySQL:**
```bash
# Windows (if using XAMPP/WAMP)
# Start MySQL from control panel

# Or via service
net start MySQL84
```

2. **Run Schema Script:**
```bash
mysql -u root -p < ../sql/01_schema.sql
```

3. **Load Test Data:**
```bash
mysql -u root -p < ../sql/02_seed_data.sql
```

4. **Verify Data:**
```bash
mysql -u root -p ecommerce_db
```
```sql
SELECT COUNT(*) FROM products;  -- Should return 20
SELECT COUNT(*) FROM users;     -- Should return 10
SELECT COUNT(*) FROM orders;    -- Should return 8
-- Total rows should be 92+
```

### Step 3: Configure Application

1. **Copy environment template:**
```bash
cp .env.example .env
```

2. **Edit `.env` file:**
```properties
DB_HOST=localhost
DB_PORT=3306
DB_NAME=ecommerce_db
DB_USER=root
DB_PASSWORD=your_actual_password
```

### Step 4: Build & Run

1. **Install dependencies:**
```bash
mvn clean install
```

2. **Run application:**
```bash
mvn exec:java
```

**Alternative (compiled JAR):**
```bash
mvn package
java -jar target/ecommerce-console-1.0-SNAPSHOT.jar
```

## 🎮 Using the Application

### Main Menu Options

1. **List All Products** - View all products with price, stock, SKU
2. **Search Products by Category** - Filter products by category
3. **Create New User** - Register a new customer/admin
4. **Place Order** - Create order and add multiple products
5. **View Orders for User** - See order history by user ID
6. **Update Product Price/Stock** - Modify product details
7. **Delete Product** - Remove product (respects FK constraints)
8. **List All Categories** - View all product categories
9. **List All Users** - View all registered users
10. **View Order Details** - See complete order with line items

### Example Workflow: Place an Order

1. Select option `4` (Place Order)
2. Enter user ID (e.g., `1`)
3. System creates order with auto-generated order number
4. Add products by entering Product ID and quantity
5. System validates stock availability
6. System updates product stock automatically
7. Enter `0` when done adding products
8. Order is saved with calculated total

## 📸 Screenshots Checklist

For your instructor submission, capture these screenshots:

### 1. ER Diagram in MySQL Workbench
- Open MySQL Workbench
- Database → Reverse Engineer
- Select `ecommerce_db`
- Capture final ER diagram showing all 8 tables and relationships

### 2. Proof of 50+ Rows
```sql
-- Run this query and screenshot results
SELECT 'Categories' AS table_name, COUNT(*) AS row_count FROM categories
UNION ALL SELECT 'Users', COUNT(*) FROM users
UNION ALL SELECT 'Addresses', COUNT(*) FROM addresses
UNION ALL SELECT 'Products', COUNT(*) FROM products
UNION ALL SELECT 'Orders', COUNT(*) FROM orders
UNION ALL SELECT 'Order Items', COUNT(*) FROM order_items
UNION ALL SELECT 'Payments', COUNT(*) FROM payments
UNION ALL SELECT 'Inventory Logs', COUNT(*) FROM inventory_logs;
```

### 3. Console Output - Data Retrieval
- Screenshot of "List All Products" showing formatted table
- Screenshot of "Search Products by Category" results
- Screenshot of "View Order Details" with joined data

### 4. Console Output - CRUD Operations
- Screenshot of "Create New User" success message
- Screenshot of "Place Order" workflow
- Screenshot of "Update Product Price/Stock" confirmation

## 🛠️ Technical Details

### Technologies
- **Java 17** - Modern Java features
- **MySQL 8.3** - Relational database with InnoDB engine
- **Maven 3.11** - Build automation and dependency management
- **JDBC** - Direct database connectivity (no ORM)

### Dependencies
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>dotenv-java</artifactId>
    <version>3.0.0</version>
</dependency>
```

### Design Patterns
- **DAO Pattern** - Data access abstraction
- **Factory Pattern** - Connection management
- **Repository Pattern** - CRUD operations
- **MVC-like** - Model, DAO (Controller), UI (View)

### Best Practices Implemented
- ✅ Prepared statements (SQL injection prevention)
- ✅ Try-with-resources (automatic resource cleanup)
- ✅ Connection pooling-ready architecture
- ✅ Environment-based configuration
- ✅ Proper exception handling
- ✅ DECIMAL type for monetary values
- ✅ Timestamp tracking (created_at, updated_at)
- ✅ Referential integrity enforcement

## 🧪 Testing the Application

### Test Data Included
- 5 Categories
- 10 Users (customers + 1 admin)
- 20 Products across all categories
- 8 Orders with various statuses
- 20 Order items
- 7 Payments
- 12 Addresses

### Sample Test Queries
```sql
-- View products by category
SELECT p.name, p.price, c.name as category
FROM products p
JOIN categories c ON p.category_id = c.category_id
WHERE c.name = 'Electronics';

-- View order with items (JOIN query)
SELECT o.order_number, u.email, p.name, oi.quantity, oi.subtotal
FROM orders o
JOIN users u ON o.user_id = u.user_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
WHERE o.order_id = 1;

-- Check constraint validation
INSERT INTO products (category_id, name, price, stock)
VALUES (1, 'Test Product', -10.00, 5);  -- Should FAIL (price must be > 0)
```

## 🐛 Troubleshooting

### Connection Issues
```
Error: Failed to connect to database
```
**Solution:**
1. Verify MySQL is running: `mysqladmin -u root -p status`
2. Check database exists: `mysql -u root -p -e "SHOW DATABASES;"`
3. Verify `.env` file exists and has correct credentials
4. Check firewall/port 3306 is open

### Foreign Key Constraint Errors
```
Cannot delete product: It may be referenced in orders
```
**Solution:** This is expected behavior! Foreign keys prevent data integrity issues.
- First delete order_items referencing the product
- Or set product to inactive instead of deleting

### Maven Build Errors
```
Error: Could not find or load main class
```
**Solution:**
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.ecommerce.Main"
```

## 📚 Additional Features

### Extra DAO Methods
- `UserDAO.getByEmail()` - Find user by email
- `ProductDAO.getByCategory()` - Filter products by category
- `OrderDAO.getByUserId()` - Get user's order history
- `OrderItemDAO.getByOrderId()` - Get items in an order

### Data Integrity Features
- Order total automatically calculated from order items
- Product stock decremented when orders are placed
- Unique constraints prevent duplicate emails/SKUs/order numbers
- Foreign key constraints prevent orphaned records

## 👨‍🎓 Academic Integrity Note

This project was created as a template for educational purposes. Students should:
- Understand every line of code
- Be able to explain design decisions
- Modify and extend functionality independently
- Not submit as-is without understanding

## 📄 License

Educational use only. See instructor for specific requirements.

## 🤝 Contributing

This is an academic project. No external contributions accepted.

---
