# CSCE 548 - Project 2 Documentation
## E-Commerce Application - Business Layer & REST Services

### 📋 Project Overview
This project extends the data layer from Project 1 by adding:
1. **Business Layer** - Service classes with business logic
2. **Service/API Layer** - RESTful web services using Spring Boot
3. **Console Client** - Test application that calls the REST services
4. **Hosting Support** - Multiple deployment options with detailed instructions

---

## 🏗️ Architecture

The application follows a **3-tier architecture**:

```
┌─────────────────────┐
│  Console Client     │ ← Test application
│  (ServiceTester)    │
└──────────┬──────────┘
           │ HTTP REST calls
           ↓
┌─────────────────────┐
│  Service Layer      │ ← REST API Controllers
│  (Controllers)      │   - UserController
│                     │   - ProductController
└──────────┬──────────┘   - CategoryController
           │               - OrderController
           ↓
┌─────────────────────┐
│  Business Layer     │ ← Business Logic & Validation
│  (Services)         │   - UserService
│                     │   - ProductService
└──────────┬──────────┘   - CategoryService
           │               - OrderService
           ↓
┌─────────────────────┐
│  Data Layer         │ ← Database Access
│  (DAOs)             │   - UserDAO/Impl
│                     │   - ProductDAO/Impl
└──────────┬──────────┘   - CategoryDAO/Impl
           │               - OrderDAO/Impl
           ↓
┌─────────────────────┐
│  Database           │ ← MySQL Database
│  (MySQL)            │
└─────────────────────┘
```

---

## 📁 Project Structure

```
ecommerce-console/
├── pom.xml                          # Maven configuration with Spring Boot
├── Dockerfile                       # Docker containerization config
├── Procfile                         # Heroku deployment config
├── .env                             # Database configuration
├── src/main/
│   ├── java/com/ecommerce/
│   │   ├── Application.java         # Spring Boot main application
│   │   ├── Main.java                # Original console app
│   │   ├── controller/              # REST API Layer (Service Layer)
│   │   │   ├── UserController.java
│   │   │   ├── ProductController.java
│   │   │   ├── CategoryController.java
│   │   │   └── OrderController.java
│   │   ├── service/                 # Business Layer
│   │   │   ├── UserService.java
│   │   │   ├── ProductService.java
│   │   │   ├── CategoryService.java
│   │   │   └── OrderService.java
│   │   ├── client/                  # Test Client
│   │   │   ├── RestClient.java
│   │   │   └── ServiceTester.java
│   │   ├── dao/                     # Data Access Layer
│   │   │   └── [All DAO interfaces and implementations]
│   │   ├── model/                   # Domain Models
│   │   │   └── [All model classes]
│   │   └── db/                      # Database Connection
│   │       └── DatabaseConnection.java
│   └── resources/
│       └── application.properties   # Spring Boot configuration
└── README_PROJECT2.md              # This file
```

---

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Database setup from Project 1

### Step 1: Build the Project
```bash
mvn clean install
```

### Step 2: Start the REST API Service
```bash
mvn spring-boot:run
```

**Wait for this message:**
```
✓ Service started successfully!
✓ API available at: http://localhost:8080/api/
```

### Step 3: Test the Services

**Option A: Use the provided Service Tester**
Open a new terminal and run:
```bash
mvn exec:java -Dexec.mainClass="com.ecommerce.client.ServiceTester"
```

**Option B: Use cURL commands**
```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","passwordHash":"pass123","firstName":"John","lastName":"Doe","phone":"555-1234","role":"CUSTOMER"}'

# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Update user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"email":"updated@example.com","firstName":"Jane","lastName":"Smith","phone":"555-5678","role":"CUSTOMER"}'

# Delete user
curl -X DELETE http://localhost:8080/api/users/1
```

**Option C: Use Postman**
1. Import the API endpoints
2. Base URL: `http://localhost:8080/api`
3. Test all CRUD operations

---

## 🌐 API Endpoints

### User Endpoints
- `POST /api/users` - Create a new user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users/authenticate` - Authenticate user

### Product Endpoints
- `POST /api/products` - Create a new product
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/{id}/stock` - Check stock availability
- `PUT /api/products/{id}/stock` - Update stock quantity

### Category Endpoints
- `POST /api/categories` - Create a new category
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Order Endpoints
- `POST /api/orders` - Create a new order
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `PUT /api/orders/{id}` - Update order
- `DELETE /api/orders/{id}` - Delete order
- `GET /api/orders/user/{userId}` - Get orders by user ID
- `PUT /api/orders/{id}/status` - Update order status
- `GET /api/orders/user/{userId}/count` - Get order count for user

---

## 🖥️ Hosting Options

### 1. Local Development
**Platform:** Local machine  
**Command:** `mvn spring-boot:run`  
**URL:** http://localhost:8080

### 2. Heroku
**Platform:** Heroku Cloud  
**Free Tier:** Available  
**Instructions:**
```bash
# Install Heroku CLI
# https://devcenter.heroku.com/articles/heroku-cli

# Login to Heroku
heroku login

# Create app
heroku create your-app-name

# Add MySQL database
heroku addons:create jawsdb:kitefin

# Set environment variables
heroku config:set DB_HOST=your-db-host
heroku config:set DB_USER=your-user
heroku config:set DB_PASSWORD=your-password
heroku config:set DB_NAME=ecommerce_db

# Deploy
git push heroku main

# Open app
heroku open
```

### 3. Docker
**Platform:** Any Docker host  
**Instructions:**
```bash
# Build image
docker build -t ecommerce-api .

# Run container
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_USER=your-user \
  -e DB_PASSWORD=your-password \
  ecommerce-api
```

### 4. AWS Elastic Beanstalk
**Platform:** Amazon Web Services  
**Instructions:**
```bash
# Package application
mvn clean package

# Install AWS EB CLI
pip install awsebcli

# Initialize
eb init -p java-17

# Create environment
eb create production

# Deploy
eb deploy
```

### 5. Azure App Service
**Platform:** Microsoft Azure  
**Instructions:**
```bash
# Install Azure CLI
# https://docs.microsoft.com/en-us/cli/azure/install-azure-cli

# Login
az login

# Create resource group
az group create --name myResourceGroup --location eastus

# Deploy
az webapp up --name your-app-name --resource-group myResourceGroup
```

**Or use VS Code:**
- Install Azure App Service extension
- Right-click project folder
- Select "Deploy to Web App"

### 6. Google Cloud Platform
**Platform:** Google Cloud  
**Instructions:**
```bash
# Install gcloud CLI
# https://cloud.google.com/sdk/docs/install

# Initialize
gcloud init

# Deploy to App Engine
gcloud app deploy

# Or deploy to Cloud Run
gcloud builds submit --tag gcr.io/PROJECT-ID/ecommerce-api
gcloud run deploy --image gcr.io/PROJECT-ID/ecommerce-api --platform managed
```

---

## 🧪 Testing Guide

The `ServiceTester` class demonstrates complete CRUD operations:

### What it Tests:
1. **Create** - POST request to create a new record
2. **Read** - GET request to retrieve the created record
3. **Update** - PUT request to modify the record
4. **Delete** - DELETE request to remove the record
5. **Verify** - GET request to confirm deletion

### Test Output:
```
=================================================
  E-Commerce REST API Service Tester
=================================================

--- TESTING CATEGORY SERVICES ---
Step 1: Creating a new category via POST /api/categories
✓ Category created with ID: 5

Step 2: Reading category via GET /api/categories/5
✓ Category retrieved: Test Electronics

Step 3: Deleting category via DELETE /api/categories/5
✓ Category deleted successfully

Step 4: Verifying deletion via GET /api/categories/5
✓ Category successfully deleted (not found)

--- TESTING USER SERVICES ---
[Similar output for User operations]

--- TESTING PRODUCT SERVICES ---
[Similar output for Product operations]

=================================================
  ALL TESTS COMPLETED SUCCESSFULLY! ✓
=================================================
```

---

## 📸 Screenshots Required

For your project submission, take screenshots of:

1. **Service startup** - Terminal showing "Service started successfully!"
2. **ServiceTester output** - Complete test execution with all checkmarks
3. **Postman/cURL** - Individual API calls showing:
   - POST request creating a record
   - GET request retrieving the record
   - PUT request updating the record
   - DELETE request removing the record
4. **Database verification** - MySQL Workbench showing the data changes
5. **Hosted service** - If deployed, show the service running on cloud platform

---

## 📚 Code Highlights

### Business Layer Example
```java
// UserService.java - Business logic layer
public User registerUser(User user) throws SQLException {
    // Business logic: Validate email format
    if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email format");
    }
    // Call data layer
    return userDAO.create(user);
}
```

### Service Layer Example
```java
// UserController.java - REST API endpoint
@PostMapping
public ResponseEntity<?> registerUser(@RequestBody User user) {
    try {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
```

### Client Layer Example
```java
// RestClient.java - Calling the service
public User createUser(User user) throws Exception {
    String json = objectMapper.writeValueAsString(user);
    HttpPost request = new HttpPost(baseUrl + "/users");
    request.setEntity(new StringEntity(json));
    request.setHeader("Content-Type", "application/json");
    // Execute HTTP request to service
    CloseableHttpResponse response = httpClient.execute(request);
    return objectMapper.readValue(responseBody, User.class);
}
```

---

## ✅ Project Checklist

- [x] Business layer created with all DAO methods available
- [x] Service layer (REST API) created with all business methods available
- [x] Hosting instructions added as comments in code
- [x] Console-based test client created
- [x] All CRUD operations tested (Create, Read, Update, Delete)
- [x] Code thoroughly documented
- [ ] Take screenshots of service execution
- [ ] Take screenshots of data retrieval
- [ ] Commit code to GitHub repository

---

## 🔧 Troubleshooting

### Service won't start
- Check if port 8080 is already in use
- Verify database connection in .env file
- Ensure MySQL is running

### Tests failing
- Make sure REST API service is running first
- Check if database has required tables
- Verify network connectivity to localhost:8080

### HTTP 500 errors
- Check database credentials
- Verify database schema matches model classes
- Review service logs for stack traces

---

## 👨‍💻 Technologies Used

- **Java 17** - Programming language
- **Spring Boot 3.2** - REST API framework
- **Maven** - Build tool
- **Apache HttpClient 5** - HTTP client for testing
- **Jackson** - JSON processing
- **MySQL** - Database
- **Docker** - Containerization (optional)

---

## 📝 Assignment Completion

This project successfully demonstrates:

1. ✅ **Business Layer** - All DAO methods wrapped with business logic
2. ✅ **Service Layer** - RESTful endpoints exposing business methods
3. ✅ **Hosting Documentation** - Detailed instructions for 6 platforms
4. ✅ **Test Client** - Console application testing all CRUD operations
5. ✅ **Complete Testing** - All services thoroughly tested
6. ✅ **Code Documentation** - Comments explaining architecture and usage

---

## 📄 License
CSCE 548 - Database System Design  
University of South Carolina  
Project 2 - February 2026
