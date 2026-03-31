# E-Commerce N-Tier Application

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com)
[![License: Educational](https://img.shields.io/badge/License-Educational-lightgrey.svg)](LICENSE)

A fully functional **4-tier e-commerce application** built for CSCE 548 (Database Management Systems), demonstrating a complete n-tier architecture from a MySQL relational database through a Java Spring Boot REST API to a browser-based web client.

> **CSCE 548 – Project 4** · Spring 2026

---

## 📐 Architecture

```
[Browser Web Client]  ←→  [Spring Boot REST API]  ←→  [Business Services]  ←→  [MySQL 8]
  HTML / CSS / JS            port 8080                    Java Services          ecommerce_db
  (Tier 4 – Client)       (Tier 3 – Service)           (Tier 2 – Business)   (Tier 1 – Data)
```

---

## 🏗️ Project Structure

```
ecommerce-console/
├── sql/
│   ├── 01_schema.sql        # Database schema – 8 tables, 9 FK constraints
│   └── 02_seed_data.sql     # Test data – 92 rows across all tables
├── src/main/java/com/ecommerce/
│   ├── model/               # POJOs: Category, User, Product, Order, OrderItem, Address, Payment
│   ├── dao/                 # DAO interfaces + JDBC implementations
│   ├── db/                  # DatabaseConnection utility (.env-based config)
│   ├── service/             # Business layer: validation & orchestration
│   ├── controller/          # Spring REST controllers (service layer)
│   └── Application.java     # Spring Boot entry point
├── web-client/
│   ├── index.html           # Single-page application UI
│   ├── app.js               # All API calls and DOM logic
│   └── styles.css           # Responsive styling
├── pom.xml                  # Maven – Spring Boot 3.2, MySQL connector, dotenv
├── .env.example             # Database credentials template
├── DEPLOYMENT.md            # Full step-by-step deployment guide
└── README.md                # This file
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

1. **Build (download dependencies + compile):**
```bash
mvn clean package -DskipTests
```

2. **Run the REST API:**
```bash
mvn spring-boot:run
# or
java -jar target/ecommerce-console-1.0-SNAPSHOT.jar
```

3. **Open the web client:**
```bash
# Option A – open directly
start web-client/index.html

# Option B – serve on localhost:3000 (avoids CORS issues)
python -m http.server 3000 --directory web-client
```

## 🎮 Using the Application

### Starting the Back End

```powershell
mvn spring-boot:run
```

The REST API starts on **http://localhost:8080**.

### Starting the Front End

Open `web-client/index.html` in a browser, or serve it locally:

```powershell
cd web-client
python -m http.server 3000
# then open http://localhost:3000
```

Click **Test Connection** in the header — you should see **✓ Connected**.

### Example Workflow: Create and Update a Product

1. Open the **Products** tab
2. Fill in the **Create Product** form (Category ID, Name, SKU, Price, Stock) and click **Create Product**
3. Note the returned `productId`
4. Fill in the **Update Product** form with that ID and changed values, click **Update Product**
5. Click **Get All Products** to confirm the change
6. Enter the ID in **Delete Product** and click the button to remove it

## 📸 Screenshots Checklist (Project 4)

### 1. Web Client – GET Operations
- **Products tab → Get All Products** – shows table of 20 products
- **Products tab → Get Product by ID** (e.g., ID 1) – shows single item card
- Repeat for Categories, Users, Orders

### 2. Web Client – POST (Create)
- Fill Create Product form → click **Create Product** → screenshot the JSON response
- Confirm in MySQL: `SELECT * FROM products ORDER BY product_id DESC LIMIT 1;`

### 3. Web Client – PUT (Update)
- Fill Update Product form with existing ID → click **Update Product** → screenshot response
- Confirm in MySQL: `SELECT name, price, stock FROM products WHERE product_id = <id>;`

### 4. Web Client – DELETE
- Enter an ID → click **Delete Product** → screenshot success message
- Confirm in MySQL: `SELECT * FROM products WHERE product_id = <id>;` (empty result)

### 5. Database Verification Query
```sql
SELECT 'categories' t, COUNT(*) r FROM categories
UNION ALL SELECT 'users', COUNT(*) FROM users
UNION ALL SELECT 'addresses', COUNT(*) FROM addresses
UNION ALL SELECT 'products', COUNT(*) FROM products
UNION ALL SELECT 'orders', COUNT(*) FROM orders
UNION ALL SELECT 'order_items', COUNT(*) FROM order_items
UNION ALL SELECT 'payments', COUNT(*) FROM payments
UNION ALL SELECT 'inventory_logs', COUNT(*) FROM inventory_logs;
```

## 🛠️ Technical Details

### Technologies
- **Java 17** - Modern Java features
- **Spring Boot 3.2** - REST API service layer
- **MySQL 8.0+** - Relational database with InnoDB engine
- **Maven 3.6+** - Build automation and dependency management
- **JDBC** - Direct database connectivity (no ORM)
- **HTML / CSS / JavaScript** - Browser-based client layer (Tier 4)

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
mvn spring-boot:run
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

**Built with ❤️ for CSCE548 - Database Management Systems**
