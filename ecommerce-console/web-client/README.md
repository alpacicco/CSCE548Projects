# E-Commerce Web Client - Project 4

## Overview
This is a browser-based front-end client for the E-Commerce REST API. It provides full CRUD (Create, Read, Update, Delete) functionality for all four entities: Products, Orders, Categories, and Users.

## Features Implemented

### ✅ Full CRUD for Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/{id}/stock` - Check product stock status
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update an existing product
- `DELETE /api/products/{id}` - Delete a product

### ✅ Full CRUD for Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get orders by user ID
- `GET /api/orders/user/{userId}/count` - Get user's order count
- `POST /api/orders` - Create a new order
- `PUT /api/orders/{id}` - Update an existing order
- `DELETE /api/orders/{id}` - Delete an order

### ✅ Full CRUD for Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create a new category
- `PUT /api/categories/{id}` - Update an existing category
- `DELETE /api/categories/{id}` - Delete a category

### ✅ Full CRUD for Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Register a new user
- `PUT /api/users/{id}` - Update an existing user
- `DELETE /api/users/{id}` - Delete a user

## How to Run

### Step 1: Start the REST API Server
```powershell
cd C:\Users\Owner\Desktop\CSCE548\CSCE548Projects\ecommerce-console
mvn spring-boot:run
```
The server starts on `http://localhost:8080`.

### Step 2: Open the Web Client

**Option A: Open directly**
```powershell
cd web-client
start index.html
```

**Option B: Serve locally (recommended – avoids CORS issues)**
```powershell
python -m http.server 3000 --directory web-client
# then open http://localhost:3000
```

### Step 3: Use the Client
1. The page auto-tests connection to `http://localhost:8080`
2. Click through tabs: **Products**, **Orders**, **Categories**, **Users**
3. Use GET buttons to read data, and the Create / Update / Delete forms to mutate data

## Taking Screenshots for Project 4

Capture before/after screenshots for each CRUD operation:

1. **GET All** – table of all records
2. **GET by ID** – single item card
3. **POST (Create)** – fill form, submit, screenshot JSON response + MySQL `SELECT`
4. **PUT (Update)** – fill form with changed values, submit, confirm in MySQL
5. **DELETE** – enter ID, submit, confirm empty `SELECT` in MySQL

## Troubleshooting

### "Connection Failed" Error
- Confirm Spring Boot is running: `mvn spring-boot:run`
- Check that the API URL field shows `http://localhost:8080`

### "No data found"
- Ensure seed data was loaded: `mysql -u root -p < sql/02_seed_data.sql`
- Check the server console for stack traces

### CORS Issues
- All controllers use `@CrossOrigin(origins = "*")` – should not be an issue
- If opening via `file://`, use the Python HTTP server method instead

## Files Structure
```
web-client/
├── index.html    # Single-page application UI
├── styles.css    # Responsive styling
├── app.js        # All API calls and DOM logic
└── README.md     # This file
```

## Technology Stack
- **Frontend**: Pure HTML5, CSS3, Vanilla JavaScript (no frameworks)
- **API Communication**: Fetch API
- **Hosting**: Local file system or any static web server

## Project 4 Deliverables Checklist
- ✅ Web client created with full CRUD on all four entities
- ✅ All GET methods implemented and tested
- ✅ All POST (create) methods implemented and tested
- ✅ All PUT (update) methods implemented and tested
- ✅ All DELETE methods implemented and tested
- ✅ Client hosted and functional
- 📸 Take screenshots and consolidate into PDF
- 📤 Submit PDF to Blackboard
- 📤 Submit GitHub URL to Blackboard


## Features Implemented

### ✅ All GET Methods for Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/{id}/stock` - Check product stock status

### ✅ All GET Methods for Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get orders by user ID
- `GET /api/orders/user/{userId}/count` - Get user's order count

### ✅ All GET Methods for Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID

### ✅ All GET Methods for Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email

## How to Run

### Step 1: Start the REST API Server
First, make sure your Spring Boot REST API is running:

```powershell
# Navigate to the project directory
cd C:\Users\Owner\Desktop\CSCE548\CSCE548Projects\ecommerce-console

# Start the Spring Boot server
mvn spring-boot:run
```
