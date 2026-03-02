# Deployment Document
## CSCE 548 – Project 4: N-Tier E-Commerce Application

---

## Table of Contents

1. [Architecture Overview](#1-architecture-overview)
2. [Prerequisites](#2-prerequisites)
3. [Step 1 – Download from GitHub](#3-step-1--download-from-github)
4. [Step 2 – Configure the Database (Data Layer)](#4-step-2--configure-the-database-data-layer)
5. [Step 3 – Configure the Application](#5-step-3--configure-the-application)
6. [Step 4 – Build the Back End (Business + Service Layers)](#6-step-4--build-the-back-end-business--service-layers)
7. [Step 5 – Run the REST API Service](#7-step-5--run-the-rest-api-service)
8. [Step 6 – Run the Front End (Client Layer)](#8-step-6--run-the-front-end-client-layer)
9. [Step 7 – Verify Everything Works](#9-step-7--verify-everything-works)
10. [Troubleshooting](#10-troubleshooting)

---

## 1. Architecture Overview

This application follows the classic **4-tier n-tier architecture**:

```
┌─────────────────────────────────────────────────────┐
│  CLIENT LAYER  (Tier 4)                             │
│  web-client/index.html + app.js + styles.css        │
│  Plain HTML/CSS/JavaScript – runs in any browser    │
│  Served locally (file://) or via a static host      │
└───────────────────────┬─────────────────────────────┘
                        │  HTTP / JSON (REST)
                        ▼
┌─────────────────────────────────────────────────────┐
│  SERVICE LAYER  (Tier 3)                            │
│  Spring Boot REST API  – port 8080                  │
│  com.ecommerce.controller.*Controller               │
│  Exposes: /api/products  /api/users                 │
│           /api/orders    /api/categories            │
└───────────────────────┬─────────────────────────────┘
                        │  Java method calls
                        ▼
┌─────────────────────────────────────────────────────┐
│  BUSINESS LAYER  (Tier 2)                           │
│  com.ecommerce.service.*Service                     │
│  Validation, business rules, orchestration          │
└───────────────────────┬─────────────────────────────┘
                        │  JDBC (java.sql)
                        ▼
┌─────────────────────────────────────────────────────┐
│  DATA LAYER  (Tier 1)                               │
│  com.ecommerce.dao.*DAOImpl                         │
│  MySQL 8.0+ database  ecommerce_db                  │
│  8 tables, 9 foreign keys, 92+ seed rows            │
└─────────────────────────────────────────────────────┘
```

---

## 2. Prerequisites

Install the following **before** you begin. All are free.

| Tool | Version | Purpose | Download |
|------|---------|---------|---------|
| **Java JDK** | 17 or higher | Compile and run the Spring Boot API | https://adoptium.net |
| **Apache Maven** | 3.6+ | Build the project and manage dependencies | https://maven.apache.org/download.cgi |
| **MySQL Server** | 8.0+ | Relational database (data layer) | https://dev.mysql.com/downloads/mysql/ |
| **Git** | Any recent | Clone the repository | https://git-scm.com/downloads |
| **Web browser** | Any modern | Run the front-end client | Already installed |

> **Optional – easier setup:**
> - **MySQL Workbench** – GUI for managing the database: https://dev.mysql.com/downloads/workbench/
> - **XAMPP / WAMP** – Bundles MySQL + Apache for one-click local setup (Windows/macOS)

### Verify Installations

Open a terminal (Command Prompt or PowerShell on Windows) and run:

```powershell
java -version        # Should print: openjdk 17.x.x or similar
mvn -version         # Should print: Apache Maven 3.x.x
mysql --version      # Should print: mysql  Ver 8.x.x
git --version        # Should print: git version 2.x.x
```

If any command fails, install the missing tool before proceeding.

---

## 3. Step 1 – Download from GitHub

### Option A – Clone with Git (recommended)

```bash
git clone https://github.com/alpacicco/CSCE548Projects.git
cd CSCE548Projects/ecommerce-console
```

### Option B – Download via ZIP

1. Go to: `https://github.com/alpacicco/CSCE548Projects`
2. Click **Code → Download ZIP**
3. Extract archive to a location of your choice (e.g., `C:\Projects\`)
4. Navigate into the extracted folder:
   ```powershell
   cd C:\Projects\CSCE548Projects\ecommerce-console
   ```

You should now see this structure:

```
ecommerce-console/
├── sql/
│   ├── 01_schema.sql
│   └── 02_seed_data.sql
├── src/
├── web-client/
├── pom.xml
├── .env.example
└── README.md
```

---

## 4. Step 2 – Configure the Database (Data Layer)

### 4.1 Start MySQL

**Windows (MySQL installed as a service):**
```powershell
net start MySQL84        # or whatever your service is named
# Confirm it started:
mysqladmin -u root -p status
```

**Windows (XAMPP):**
Open the XAMPP Control Panel and click **Start** next to MySQL.

**macOS / Linux:**
```bash
sudo systemctl start mysql       # Linux
brew services start mysql        # macOS with Homebrew
```

### 4.2 Create the Database Schema

From the project root directory:

```powershell
# Windows – add MySQL to your PATH (adjust path as needed)
$env:Path += ";C:\Program Files\MySQL\MySQL Server 8.4\bin"

# Run the schema script (creates ecommerce_db and all 8 tables)
mysql -u root -p < sql/01_schema.sql
```

> When prompted, enter your MySQL root password.

### 4.3 Load Seed / Test Data

```powershell
mysql -u root -p < sql/02_seed_data.sql
```

### 4.4 Verify the Data

```powershell
mysql -u root -p ecommerce_db
```

Once inside the MySQL prompt run:

```sql
SELECT
    (SELECT COUNT(*) FROM categories)     AS categories,
    (SELECT COUNT(*) FROM users)          AS users,
    (SELECT COUNT(*) FROM addresses)      AS addresses,
    (SELECT COUNT(*) FROM products)       AS products,
    (SELECT COUNT(*) FROM orders)         AS orders,
    (SELECT COUNT(*) FROM order_items)    AS order_items,
    (SELECT COUNT(*) FROM payments)       AS payments,
    (SELECT COUNT(*) FROM inventory_logs) AS inventory_logs;
```

**Expected result:**

| categories | users | addresses | products | orders | order_items | payments | inventory_logs |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 5 | 10 | 12 | 20 | 8 | 20 | 7 | 10 |

Total = **92 rows** ✓

---

## 5. Step 3 – Configure the Application

The back end reads database credentials from a `.env` file.

### 5.1 Create the .env File

From the project root:

```powershell
copy .env.example .env        # Windows
# or
cp .env.example .env          # macOS / Linux
```

### 5.2 Edit the .env File

Open `.env` in any text editor (Notepad, VS Code, etc.) and set your MySQL password:

```properties
DB_HOST=localhost
DB_PORT=3306
DB_NAME=ecommerce_db
DB_USER=root
DB_PASSWORD=your_mysql_password_here
```

> If you have no root password (default after fresh MySQL install), leave `DB_PASSWORD=` blank.

---

## 6. Step 4 – Build the Back End (Business + Service Layers)

From the `ecommerce-console/` directory:

```powershell
mvn clean package -DskipTests
```

This command:
- Downloads all Maven dependencies (~300 MB on first run)
- Compiles all Java source files
- Packages everything into a runnable JAR: `target/ecommerce-console-1.0-SNAPSHOT.jar`

**Expected output (last lines):**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 30-60 seconds
```

If you see `BUILD FAILURE`, see the [Troubleshooting](#10-troubleshooting) section.

---

## 7. Step 5 – Run the REST API Service

### Option A – Maven (development)

```powershell
mvn spring-boot:run
```

### Option B – Packaged JAR

```powershell
java -jar target/ecommerce-console-1.0-SNAPSHOT.jar
```

**Expected startup output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
...
Started Application in 4.3 seconds (JVM running for 5.1)
```

The REST API is now listening on **http://localhost:8080**.

### Confirm the Service is Running

Open a new terminal and run:

```powershell
curl http://localhost:8080/api/categories
```

You should see a JSON array of the 5 categories.  
Or open that URL directly in your browser.

### Available API Endpoints

| Resource | GET all | GET by ID | POST (create) | PUT (update) | DELETE |
|----------|---------|-----------|--------------|-------------|--------|
| Products | `GET /api/products` | `GET /api/products/{id}` | `POST /api/products` | `PUT /api/products/{id}` | `DELETE /api/products/{id}` |
| Users | `GET /api/users` | `GET /api/users/{id}` | `POST /api/users` | `PUT /api/users/{id}` | `DELETE /api/users/{id}` |
| Orders | `GET /api/orders` | `GET /api/orders/{id}` | `POST /api/orders` | `PUT /api/orders/{id}` | `DELETE /api/orders/{id}` |
| Categories | `GET /api/categories` | `GET /api/categories/{id}` | `POST /api/categories` | `PUT /api/categories/{id}` | `DELETE /api/categories/{id}` |

---

## 8. Step 6 – Run the Front End (Client Layer)

The client is a pure HTML/CSS/JavaScript single-page app — **no build step required**.

### Option A – Open directly in a browser (simplest)

In File Explorer, navigate to `web-client/` and double-click **`index.html`**.  
The app opens in your default browser at a `file://...` path.

### Option B – Serve with a simple HTTP server (recommended to avoid CORS issues)

If you have Python installed:

```powershell
cd web-client
python -m http.server 3000
```

Then open: **http://localhost:3000** in your browser.

**Or with Node.js:**

```powershell
npx serve web-client
```

### Configure the API URL

When the client opens, your API URL field will pre-populate with `http://localhost:8080`.  
Click **Test Connection** — you should see **✓ Connected** in green.

> If you deployed the back end to a cloud host (Heroku, Railway, etc.),  
> replace `http://localhost:8080` with your cloud URL before clicking Test Connection.

---

## 9. Step 7 – Verify Everything Works

Use the web client UI to test each layer end-to-end.  Take screenshots for your submission.

### Full CRUD Test Checklist

**Products tab:**
1. Click **Get All Products** → should display a table of 20 products
2. Enter ID `1` → **Get Product by ID** → should show Laptop Pro 15
3. Fill in the Create Product form → **Create Product** → confirm in MySQL: `SELECT * FROM products ORDER BY product_id DESC LIMIT 1;`
4. Enter the new product's ID → fill Update form → **Update Product** → confirm in MySQL
5. Enter the ID → **Delete Product** → confirm in MySQL: `SELECT * FROM products WHERE product_id = <id>;` (should return empty)

**Categories, Users, Orders tabs:** repeat for each entity.

### Sample Database Verification Queries

```sql
-- Confirm latest product
SELECT product_id, name, price, stock FROM products ORDER BY product_id DESC LIMIT 5;

-- Confirm latest user
SELECT user_id, email, first_name, role FROM users ORDER BY user_id DESC LIMIT 5;

-- Confirm latest order with buyer name
SELECT o.order_id, o.order_number, o.status, u.email
FROM orders o JOIN users u ON o.user_id = u.user_id
ORDER BY o.order_id DESC LIMIT 5;

-- Confirm total row count
SELECT 'categories' t, COUNT(*) r FROM categories
UNION ALL SELECT 'users', COUNT(*) FROM users
UNION ALL SELECT 'addresses', COUNT(*) FROM addresses
UNION ALL SELECT 'products', COUNT(*) FROM products
UNION ALL SELECT 'orders', COUNT(*) FROM orders
UNION ALL SELECT 'order_items', COUNT(*) FROM order_items
UNION ALL SELECT 'payments', COUNT(*) FROM payments
UNION ALL SELECT 'inventory_logs', COUNT(*) FROM inventory_logs;
```

---

## 10. Troubleshooting

### "Access denied" connecting to MySQL

- Verify your password in `.env` is correct.
- Try connecting manually: `mysql -u root -p`  
- If you forgot root password, reset it: https://dev.mysql.com/doc/refman/8.0/en/resetting-permissions.html

### "Unknown database 'ecommerce_db'"

You forgot to run the schema script. Run:
```powershell
mysql -u root -p < sql/01_schema.sql
```

### Spring Boot won't start – port 8080 already in use

```powershell
# Find what's using port 8080
netstat -ano | findstr :8080
# Kill it (replace <PID> with the number from the output)
taskkill /PID <PID> /F
```

Or change the port in `src/main/resources/application.properties`:
```properties
server.port=9090
```
Then update the API URL in the web client to `http://localhost:9090`.

### Maven BUILD FAILURE – compilation errors

```powershell
# Clean and retry
mvn clean compile
# Or see full output
mvn clean package -e
```

Make sure your JDK is version 17+:
```powershell
java -version
```

### Web client shows "✗ Connection Failed"

1. Confirm the Spring Boot back end is running.
2. Check the API URL field – it must match the host/port the service is on.
3. If opening `index.html` directly as `file://`, CORS rules on some browsers block requests. Use the Python HTTP server method instead.

### Foreign Key Constraint Error on Delete

Some records cannot be deleted while related records exist.  
Example: Cannot delete a product that has `order_items` rows.  
Either delete the child records first, or set `is_active = false` instead of deleting.

---

*Document prepared for CSCE 548 – Project 4*  
*Spring 2026*
