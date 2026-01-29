# 📋 DEMONSTRATION GUIDE
## How to Present Your E-Commerce Application

---

## ✅ REQUIREMENTS CHECKLIST

Your application meets **ALL** requirements as specified:

### Database Requirements ✓
- ✅ **Minimum 5 tables** - You have **8 tables**
- ✅ **Minimum 3 foreign keys** - You have **9 foreign key relationships**
- ✅ **Minimum 50 rows** - You have **89-92 rows** of test data
- ✅ **Professional schema** - Includes constraints, indexes, timestamps
- ✅ **Relational integrity** - All FK relationships enforced

### Application Requirements ✓
- ✅ **Console-based interface** - Interactive menu system
- ✅ **Data retrieval** - Multiple query options with formatting
- ✅ **CRUD operations** - Create, Read, Update, Delete implemented
- ✅ **Java implementation** - Java 17 with JDBC
- ✅ **Model/DAO architecture** - 7 POJOs + 5 DAO implementations

---

## 🎬 STEP-BY-STEP DEMONSTRATION

### **PART 1: DATABASE STRUCTURE (5 minutes)**

#### Show ER Diagram
1. Open MySQL Workbench
2. Connect to your local MySQL server
3. Go to: **Database → Reverse Engineer**
4. Select your `ecommerce_db` database
5. Click through wizard to generate ER diagram
6. **SCREENSHOT THIS** - Shows all 8 tables with relationship lines

**What to Point Out:**
- 8 tables visible (categories, users, addresses, products, orders, order_items, payments, inventory_logs)
- Lines connecting tables show foreign key relationships
- Primary keys marked with key icons

#### Verify Row Counts
Open terminal and run:
```bash
mysql -u root -p ecommerce_db
```

Then execute:
```sql
-- Show total row count
SELECT 'Total Rows' as Metric,
    (SELECT COUNT(*) FROM categories) +
    (SELECT COUNT(*) FROM users) +
    (SELECT COUNT(*) FROM addresses) +
    (SELECT COUNT(*) FROM products) +
    (SELECT COUNT(*) FROM orders) +
    (SELECT COUNT(*) FROM order_items) +
    (SELECT COUNT(*) FROM payments) +
    (SELECT COUNT(*) FROM inventory_logs) as Row_Count;
```

**Expected Result:** Total Rows: **89** (exceeds 50+ requirement)

**SCREENSHOT THIS** - Proof of data

#### Show Detailed Table Counts
```sql
SELECT 'categories' AS Table_Name, COUNT(*) as Rows FROM categories
UNION ALL SELECT 'users', COUNT(*) FROM users
UNION ALL SELECT 'addresses', COUNT(*) FROM addresses
UNION ALL SELECT 'products', COUNT(*) FROM products
UNION ALL SELECT 'orders', COUNT(*) FROM orders
UNION ALL SELECT 'order_items', COUNT(*) FROM order_items
UNION ALL SELECT 'payments', COUNT(*) FROM payments
UNION ALL SELECT 'inventory_logs', COUNT(*) FROM inventory_logs;
```

**SCREENSHOT THIS** - Breakdown by table

---

### **PART 2: SHOW DATABASE CONSTRAINTS (3 minutes)**

#### Demonstrate Foreign Key Relationships
```sql
-- Show all foreign keys in database
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'ecommerce_db'
AND REFERENCED_TABLE_NAME IS NOT NULL
ORDER BY TABLE_NAME;
```

**Expected Output:** Lists all 9 foreign key relationships

**What This Proves:** Your database has proper referential integrity

#### Show Unique Constraints
```sql
-- Show unique constraints
SELECT TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'ecommerce_db'
AND CONSTRAINT_NAME LIKE 'UQ_%';
```

**Expected Output:** Shows unique constraints on email, SKU, order_number, transaction_id

#### Test Check Constraints (Optional but Impressive)
```sql
-- Try to insert negative price (should fail)
INSERT INTO products (category_id, name, price, stock, sku)
VALUES (1, 'Invalid', -10.00, 5, 'TEST-NEG-001');
```

**Expected Error:** `Check constraint 'products_chk_1' is violated.`

**What This Proves:** Your CHECK constraints are working

---

### **PART 3: RUN THE APPLICATION (10 minutes)**

#### Start the Application
Open PowerShell in project directory:
```powershell
cd C:\Users\Owner\Desktop\CSCE548\CSCE548Projects\ecommerce-console
mvn exec:java
```

OR double-click `run.bat`

**Wait for:** Menu to appear with 10 options

---

### **DEMONSTRATION SCRIPT**

#### Demo 1: List All Products (READ)
**Action:**
1. Type `1` and press Enter
2. Wait for products to display

**SCREENSHOT THIS** - Shows formatted table with all 20 products

**What to Say:**
> "This demonstrates basic data retrieval from the products table. Notice the formatted output with columns for ID, name, price, stock, SKU, and active status. All 20 products are displayed."

---

#### Demo 2: Search by Category (JOIN Query)
**Action:**
1. Press Enter to return to menu
2. Type `2` and press Enter
3. Type `1` (for Electronics category)
4. Press Enter

**SCREENSHOT THIS** - Shows filtered results

**What to Say:**
> "This query performs a JOIN between products and categories tables, filtering by category ID. Notice only electronics products are shown."

**Try Another:**
- Enter `2` again and try category `2` (Clothing)

---

#### Demo 3: View Order Details (Complex JOIN)
**Action:**
1. Return to menu
2. Type `10` and press Enter
3. Type `1` (for order ID 1)

**SCREENSHOT THIS** - Shows customer info + order items

**What to Say:**
> "This demonstrates a complex JOIN query across 4 tables: orders, users, order_items, and products. It shows the customer name, order status, and all items in the order with product names and prices."

---

#### Demo 4: Create New User (INSERT)
**Action:**
1. Return to menu
2. Type `3` and press Enter
3. Enter the following:
   - Email: `demo@test.com`
   - First Name: `Demo`
   - Last Name: `Student`
   - Phone: `555-1234`
   - Role: Just press Enter (uses default CUSTOMER)

**SCREENSHOT THIS** - Shows success message with generated user ID

**What to Say:**
> "This demonstrates INSERT operation. The system automatically generates a unique user ID, hashes the password, and sets timestamps. Notice the success message shows the new user ID."

---

#### Demo 5: List All Users (Verify Insert)
**Action:**
1. Type `9` and press Enter
2. Scroll to bottom to see your new user

**SCREENSHOT THIS** - Shows user list including new user

**What to Say:**
> "Here we can verify the user was successfully inserted into the database. Notice our new user 'Demo Student' appears at the bottom of the list."

---

#### Demo 6: Update Product (UPDATE)
**Action:**
1. Return to menu
2. Type `6` and press Enter
3. Enter product ID: `1`
4. New price: `159.99` (or any price)
5. New stock: `55` (or any number)

**SCREENSHOT THIS** - Shows update confirmation

**What to Say:**
> "This demonstrates UPDATE operation. The system modifies the product record and automatically updates the updated_at timestamp."

---

#### Demo 7: List Products (Verify Update)
**Action:**
1. Type `1` and press Enter
2. Look for Product ID 1

**What to Say:**
> "We can see the product now shows the updated price and stock quantity, confirming the UPDATE was successful."

---

#### Demo 8: Place Order (Complex Transaction)
**Action:**
1. Return to menu
2. Type `4` and press Enter
3. Enter user ID: `1` (John Doe)
4. Add first product:
   - Product ID: `2` (Smart Watch Pro)
   - Quantity: `1`
5. Add second product:
   - Product ID: `5` (Bluetooth Speaker)
   - Quantity: `2`
6. Type `0` to finish

**SCREENSHOT THIS** - Shows order creation process

**What to Say:**
> "This demonstrates a complex transaction that:
> 1. Creates a new order record
> 2. Inserts multiple order_item records
> 3. Updates product stock quantities
> 4. Calculates and stores the order total
> Notice the auto-generated order number and the calculated total amount."

---

#### Demo 9: View User Orders (Verify Order Created)
**Action:**
1. Type `5` and press Enter
2. Enter user ID: `1`

**SCREENSHOT THIS** - Shows order list for user

**What to Say:**
> "Here we can see all orders for user John Doe, including the order we just created. Notice the status is 'PENDING' and the total matches what we calculated."

---

#### Demo 10: Demonstrate Foreign Key Constraint (DELETE with Protection)
**Action:**
1. Return to menu
2. Type `7` and press Enter
3. Enter product ID: `1` (has existing orders)

**Expected:** Error message about foreign key constraint

**SCREENSHOT THIS** - Shows FK constraint error

**What to Say:**
> "This demonstrates foreign key constraint protection. Product 1 cannot be deleted because it has associated order items. This maintains referential integrity."

**Then try:**
1. Type `7` again
2. Enter product ID: `19` or `20` (products with no orders)
3. Confirm deletion

**SCREENSHOT THIS** - Shows successful deletion

**What to Say:**
> "Now product 20 was successfully deleted because it has no dependent records. The system properly enforces referential integrity."

---

#### Demo 11: List Categories
**Action:**
1. Type `8` and press Enter

**SCREENSHOT THIS** - Shows all 5 categories

**What to Say:**
> "This shows all 5 product categories in our system. Each category can have multiple products associated with it."

---

#### Exit Application
**Action:**
1. Type `0` and press Enter

**What to Say:**
> "And we can cleanly exit the application."

---

## 📊 REQUIREMENTS MAPPING

| Requirement | Implementation | Evidence |
|-------------|----------------|----------|
| Minimum 5 tables | 8 tables created | ER diagram + SHOW TABLES |
| Minimum 3 foreign keys | 9 FK relationships | INFORMATION_SCHEMA query |
| Minimum 50 rows | 89 rows total | Row count query |
| Data retrieval | Multiple SELECT queries | Options 1, 2, 5, 8, 9, 10 |
| CREATE operation | INSERT statements | Options 3, 4 |
| READ operation | SELECT with formatting | Options 1, 2, 5, 8, 9, 10 |
| UPDATE operation | UPDATE statements | Option 6 |
| DELETE operation | DELETE with FK checks | Option 7 |
| Console UI | Menu-driven interface | Running application |
| Professional code | MVC architecture | Source code structure |

---

## 📸 REQUIRED SCREENSHOTS SUMMARY

### For Your Submission, Capture:

1. **ER Diagram** (MySQL Workbench reverse engineered)
2. **Total Row Count Query Result** (shows 89+ rows)
3. **Detailed Table Counts** (breakdown by table)
4. **Foreign Key Relationships** (INFORMATION_SCHEMA query)
5. **Console: Main Menu** (shows all 10 options)
6. **Console: List All Products** (formatted table)
7. **Console: Search by Category** (JOIN query result)
8. **Console: View Order Details** (complex JOIN)
9. **Console: Create New User** (INSERT success)
10. **Console: User List** (showing new user)
11. **Console: Update Product** (UPDATE confirmation)
12. **Console: Place Order** (transaction workflow)
13. **Console: View User Orders** (verify order created)
14. **Console: Delete with FK Error** (constraint protection)
15. **Console: Delete Success** (when no dependencies)

**Minimum:** Screenshots 1, 2, 5, 6, 8, 9, 12 (7 screenshots cover all requirements)

---

## 🎯 KEY TALKING POINTS

### Database Design Excellence
- "I designed a normalized database schema with 8 tables representing a real e-commerce system"
- "All tables have primary keys, and I implemented 9 foreign key relationships to maintain referential integrity"
- "I added CHECK constraints to prevent invalid data like negative prices"
- "UNIQUE constraints ensure no duplicate emails or SKUs"

### Code Quality
- "I used a layered architecture: Model layer (POJOs), DAO layer (data access), UI layer (console interface)"
- "Each DAO implements full CRUD operations with proper exception handling"
- "I used PreparedStatements to prevent SQL injection attacks"
- "The application uses environment variables for secure configuration"

### Application Features
- "The console UI provides 10 different operations covering all CRUD requirements"
- "Complex queries use JOINs to combine data from multiple tables"
- "Order placement is a transaction that updates multiple tables atomically"
- "The system enforces business rules like stock validation and foreign key constraints"

### Test Data
- "I created comprehensive test data with 89 rows across all tables"
- "The data represents realistic e-commerce scenarios with customers, products, and orders"
- "Relationships between tables are fully populated to demonstrate joins"

---

## ⏱️ ESTIMATED TIME

- Part 1 (Database Structure): 5 minutes
- Part 2 (Constraints): 3 minutes
- Part 3 (Application Demo): 10 minutes
- **Total: 15-20 minutes**

---

## 🚨 TROUBLESHOOTING DURING DEMO

### If application won't start:
```powershell
# Verify MySQL is running
mysql --version
mysql -u root -p -e "SELECT 1"

# Rebuild project
mvn clean install
```

### If connection fails:
- Check `.env` file has correct password
- Verify database exists: `mysql -u root -p -e "SHOW DATABASES;"`

### If data is missing:
```bash
# Reload seed data
mysql -u root -p < sql/02_seed_data.sql
```

---

## 💡 BONUS POINTS

### If You Want to Impress Further:

1. **Show the Code:**
   - Open `ConsoleUI.java` to show JOIN query construction
   - Show `ProductDAOImpl.java` to show PreparedStatement usage

2. **Explain Design Decisions:**
   - Why you chose certain data types
   - How you normalized the schema
   - Why you included inventory_logs (audit trail)

3. **Discuss Scalability:**
   - How indexes improve query performance
   - How the DAO pattern allows switching databases
   - How the schema could grow (add reviews, wishlists, etc.)

---

## ✅ SUCCESS CRITERIA

You can confidently present when:
- ✅ MySQL is running with database populated
- ✅ Application starts and shows menu
- ✅ All 10 menu options work correctly
- ✅ You have all required screenshots captured
- ✅ You can explain the schema and relationships
- ✅ You can demonstrate CRUD operations

---

**Good luck with your demonstration! You have a complete, professional application that exceeds all requirements.**
