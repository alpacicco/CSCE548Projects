# E-Commerce Web Client - Project 3

## Overview
This is a web-based front-end client for the E-Commerce REST API (Project 2). It demonstrates all GET methods for all database tables (Products, Orders, Categories, and Users).

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

The server should start on `http://localhost:8080`

### Step 2: Open the Web Client
Simply open the `index.html` file in a web browser:

**Option A: Double-click the file**
- Navigate to `ecommerce-console/web-client/`
- Double-click `index.html`

**Option B: Use PowerShell**
```powershell
cd web-client
start index.html
```

**Option C: Right-click and "Open with"**
- Right-click `index.html`
- Choose "Open with" → Your preferred browser (Chrome, Edge, Firefox)

### Step 3: Use the Client
1. The page will automatically test the connection to `http://localhost:8080`
2. Click through the tabs: **Products**, **Orders**, **Categories**, **Users**
3. Use the buttons to call different GET endpoints
4. Results will display in a formatted table or card view

## Taking Screenshots for Project 3

To fulfill the project requirements, take screenshots of:

1. **Products Tab**
   - Get All Products
   - Get Product by ID (pick an ID from the list)
   - Get Products by Category (use a category ID)
   - Check Stock Status

2. **Orders Tab**
   - Get All Orders
   - Get Order by ID
   - Get Orders by User ID
   - Get User Order Count

3. **Categories Tab**
   - Get All Categories
   - Get Category by ID

4. **Users Tab**
   - Get All Users
   - Get User by ID
   - Get User by Email

## Troubleshooting

### "Connection Failed" Error
- Make sure the Spring Boot server is running (`mvn spring-boot:run`)
- Check that the server is on port 8080
- If using a different port, update the API URL field in the web client

### "No data found"
- Make sure your MySQL database has data
- Check the server console for any errors

### CORS Issues
- Your controllers already have `@CrossOrigin(origins = "*")` so this should work
- If you still have CORS issues, make sure your Spring Boot app is running

## Files Structure
```
web-client/
├── index.html    # Main HTML page with UI
├── styles.css    # Styling and responsive design
├── app.js        # JavaScript API calls and logic
└── README.md     # This file
```

## Technology Stack
- **Frontend**: Pure HTML5, CSS3, JavaScript (Vanilla JS - No frameworks)
- **API Communication**: Fetch API
- **Design**: Responsive design with gradient styling
- **Hosting**: Local file system (can be hosted on any web server)

## API Configuration
The default API URL is `http://localhost:8080`. You can change it:
1. At the top of the page, find the "API Base URL" field
2. Enter your API URL (e.g., `http://localhost:8081` if using different port)
3. Click "Test Connection"

## Project 3 Deliverables Checklist
- ✅ Web client created
- ✅ All GET methods for Products implemented
- ✅ All GET methods for Orders implemented
- ✅ All GET methods for Categories implemented
- ✅ All GET methods for Users implemented
- ✅ Client is hosted (local file system)
- ✅ Functionality tested
- 📸 Take screenshots and consolidate into PDF
- 📤 Submit PDF to Blackboard
- 📤 Submit GitHub URL to Blackboard

## Notes
- This web client only implements GET methods as required by Project 3
- The UI is responsive and works on desktop, tablet, and mobile
- All data is fetched in real-time from your REST API
- No additional libraries or frameworks required
