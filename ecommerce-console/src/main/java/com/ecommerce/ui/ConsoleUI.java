package com.ecommerce.ui;

import com.ecommerce.dao.*;
import com.ecommerce.model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.userDAO = new UserDAOImpl();
        this.productDAO = new ProductDAOImpl();
        this.categoryDAO = new CategoryDAOImpl();
        this.orderDAO = new OrderDAOImpl();
        this.orderItemDAO = new OrderItemDAOImpl();
    }

    public void start() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("   E-COMMERCE CONSOLE APPLICATION");
        System.out.println("═══════════════════════════════════════════");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> listAllProducts();
                    case 2 -> searchProductsByCategory();
                    case 3 -> createNewUser();
                    case 4 -> placeOrder();
                    case 5 -> viewUserOrders();
                    case 6 -> updateProductPriceStock();
                    case 7 -> deleteProduct();
                    case 8 -> listAllCategories();
                    case 9 -> listAllUsers();
                    case 10 -> viewOrderDetails();
                    case 0 -> {
                        running = false;
                        System.out.println("Thank you for using E-Commerce Console!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│              MAIN MENU                   │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. List All Products                    │");
        System.out.println("│  2. Search Products by Category          │");
        System.out.println("│  3. Create New User                      │");
        System.out.println("│  4. Place Order                          │");
        System.out.println("│  5. View Orders for User                 │");
        System.out.println("│  6. Update Product Price/Stock           │");
        System.out.println("│  7. Delete Product                       │");
        System.out.println("│  8. List All Categories                  │");
        System.out.println("│  9. List All Users                       │");
        System.out.println("│ 10. View Order Details                   │");
        System.out.println("│  0. Exit                                 │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    private void listAllProducts() throws SQLException {
        System.out.println("\n" + "=".repeat(120));
        System.out.println("ALL PRODUCTS");
        System.out.println("=".repeat(120));

        List<Product> products = productDAO.getAll();

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.printf("%-5s %-40s %-12s %-8s %-15s %-10s%n",
                "ID", "Name", "Price", "Stock", "SKU", "Active");
        System.out.println("-".repeat(120));

        for (Product product : products) {
            System.out.printf("%-5d %-40s $%-11.2f %-8d %-15s %-10s%n",
                    product.getProductId(),
                    truncate(product.getName(), 40),
                    product.getPrice(),
                    product.getStock(),
                    product.getSku(),
                    product.getIsActive() ? "Yes" : "No");
        }

        System.out.println("=".repeat(120));
        System.out.println("Total products: " + products.size());
    }

    private void searchProductsByCategory() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SEARCH PRODUCTS BY CATEGORY");
        System.out.println("=".repeat(80));

        listAllCategories();

        int categoryId = getIntInput("\nEnter category ID: ");

        List<Product> products = productDAO.getByCategory(categoryId);

        if (products.isEmpty()) {
            System.out.println("No products found in this category.");
            return;
        }

        System.out.println("\n" + "-".repeat(120));
        System.out.printf("%-5s %-40s %-12s %-8s %-15s%n",
                "ID", "Name", "Price", "Stock", "SKU");
        System.out.println("-".repeat(120));

        for (Product product : products) {
            System.out.printf("%-5d %-40s $%-11.2f %-8d %-15s%n",
                    product.getProductId(),
                    truncate(product.getName(), 40),
                    product.getPrice(),
                    product.getStock(),
                    product.getSku());
        }

        System.out.println("-".repeat(120));
        System.out.println("Total products: " + products.size());
    }

    private void createNewUser() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CREATE NEW USER");
        System.out.println("=".repeat(80));

        scanner.nextLine(); // Clear buffer

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Role (CUSTOMER/ADMIN) [CUSTOMER]: ");
        String role = scanner.nextLine().trim().toUpperCase();
        if (role.isEmpty()) {
            role = "CUSTOMER";
        }

        // Simple password hash (in production, use proper hashing like BCrypt)
        String passwordHash = "$2a$10$hash" + System.currentTimeMillis();

        User user = new User(email, passwordHash, firstName, lastName, phone, role);
        user = userDAO.create(user);

        System.out.println("\n✓ User created successfully!");
        System.out.println("User ID: " + user.getUserId());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
    }

    private void placeOrder() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PLACE ORDER");
        System.out.println("=".repeat(80));

        int userId = getIntInput("Enter User ID: ");

        User user = userDAO.getById(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("User: " + user.getFirstName() + " " + user.getLastName());

        // Generate order number
        String orderNumber = "ORD-" + System.currentTimeMillis();

        Order order = new Order(userId, orderNumber, BigDecimal.ZERO, null, null);
        order.setStatus("PENDING");
        order = orderDAO.create(order);

        System.out.println("\nOrder created: " + orderNumber);
        System.out.println("Order ID: " + order.getOrderId());

        BigDecimal totalAmount = BigDecimal.ZERO;
        boolean addingItems = true;

        while (addingItems) {
            System.out.println("\n--- Add Product to Order ---");
            listAllProducts();

            int productId = getIntInput("\nEnter Product ID (0 to finish): ");

            if (productId == 0) {
                addingItems = false;
                continue;
            }

            Product product = productDAO.getById(productId);
            if (product == null) {
                System.out.println("Product not found!");
                continue;
            }

            int quantity = getIntInput("Enter quantity: ");

            if (quantity <= 0) {
                System.out.println("Quantity must be positive!");
                continue;
            }

            if (quantity > product.getStock()) {
                System.out.println("Insufficient stock! Available: " + product.getStock());
                continue;
            }

            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));

            OrderItem orderItem = new OrderItem(order.getOrderId(), productId, quantity, product.getPrice(), subtotal);
            orderItemDAO.create(orderItem);

            // Update product stock
            product.setStock(product.getStock() - quantity);
            productDAO.update(product);

            totalAmount = totalAmount.add(subtotal);

            System.out.println("✓ Added: " + product.getName() + " x " + quantity + " = $" + subtotal);
        }

        // Update order total
        order.setTotalAmount(totalAmount);
        orderDAO.update(order);

        System.out.println("\n" + "=".repeat(80));
        System.out.println("✓ Order placed successfully!");
        System.out.println("Order Number: " + orderNumber);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("=".repeat(80));
    }

    private void viewUserOrders() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("VIEW USER ORDERS");
        System.out.println("=".repeat(80));

        int userId = getIntInput("Enter User ID: ");

        User user = userDAO.getById(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("User: " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");

        List<Order> orders = orderDAO.getByUserId(userId);

        if (orders.isEmpty()) {
            System.out.println("\nNo orders found for this user.");
            return;
        }

        System.out.println("\n" + "-".repeat(100));
        System.out.printf("%-5s %-20s %-15s %-15s %-20s%n",
                "ID", "Order Number", "Status", "Total Amount", "Order Date");
        System.out.println("-".repeat(100));

        for (Order order : orders) {
            System.out.printf("%-5d %-20s %-15s $%-14.2f %-20s%n",
                    order.getOrderId(),
                    order.getOrderNumber(),
                    order.getStatus(),
                    order.getTotalAmount(),
                    order.getOrderDate());
        }

        System.out.println("-".repeat(100));
        System.out.println("Total orders: " + orders.size());
    }

    private void updateProductPriceStock() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("UPDATE PRODUCT PRICE/STOCK");
        System.out.println("=".repeat(80));

        int productId = getIntInput("Enter Product ID: ");

        Product product = productDAO.getById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("\nCurrent Product Information:");
        System.out.println("Name: " + product.getName());
        System.out.println("Current Price: $" + product.getPrice());
        System.out.println("Current Stock: " + product.getStock());

        scanner.nextLine(); // Clear buffer

        System.out.print("\nNew Price (press Enter to keep current): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            product.setPrice(new BigDecimal(priceInput));
        }

        System.out.print("New Stock (press Enter to keep current): ");
        String stockInput = scanner.nextLine();
        if (!stockInput.isEmpty()) {
            product.setStock(Integer.parseInt(stockInput));
        }

        boolean updated = productDAO.update(product);

        if (updated) {
            System.out.println("\n✓ Product updated successfully!");
            System.out.println("New Price: $" + product.getPrice());
            System.out.println("New Stock: " + product.getStock());
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private void deleteProduct() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DELETE PRODUCT");
        System.out.println("=".repeat(80));

        int productId = getIntInput("Enter Product ID to delete: ");

        Product product = productDAO.getById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("\nProduct to delete:");
        System.out.println("ID: " + product.getProductId());
        System.out.println("Name: " + product.getName());
        System.out.println("Price: $" + product.getPrice());

        scanner.nextLine(); // Clear buffer
        System.out.print("\nAre you sure you want to delete this product? (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            try {
                boolean deleted = productDAO.delete(productId);
                if (deleted) {
                    System.out.println("\n✓ Product deleted successfully!");
                } else {
                    System.out.println("Failed to delete product.");
                }
            } catch (SQLException e) {
                System.err.println("Cannot delete product: It may be referenced in orders.");
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void listAllCategories() throws SQLException {
        List<Category> categories = categoryDAO.getAll();

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        System.out.printf("%-5s %-30s %-50s%n", "ID", "Name", "Description");
        System.out.println("-".repeat(85));

        for (Category category : categories) {
            System.out.printf("%-5d %-30s %-50s%n",
                    category.getCategoryId(),
                    category.getName(),
                    truncate(category.getDescription(), 50));
        }
    }

    private void listAllUsers() throws SQLException {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("ALL USERS");
        System.out.println("=".repeat(100));

        List<User> users = userDAO.getAll();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.printf("%-5s %-30s %-20s %-20s %-15s %-10s%n",
                "ID", "Email", "First Name", "Last Name", "Phone", "Role");
        System.out.println("-".repeat(100));

        for (User user : users) {
            System.out.printf("%-5d %-30s %-20s %-20s %-15s %-10s%n",
                    user.getUserId(),
                    truncate(user.getEmail(), 30),
                    truncate(user.getFirstName(), 20),
                    truncate(user.getLastName(), 20),
                    user.getPhone(),
                    user.getRole());
        }

        System.out.println("=".repeat(100));
        System.out.println("Total users: " + users.size());
    }

    private void viewOrderDetails() throws SQLException {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("VIEW ORDER DETAILS");
        System.out.println("=".repeat(80));

        int orderId = getIntInput("Enter Order ID: ");

        Order order = orderDAO.getById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        User user = userDAO.getById(order.getUserId());

        System.out.println("\nOrder Information:");
        System.out.println("-".repeat(80));
        System.out.println("Order Number: " + order.getOrderNumber());
        System.out.println("Status: " + order.getStatus());
        System.out
                .println("Customer: " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        System.out.println("Order Date: " + order.getOrderDate());
        System.out.println("Total Amount: $" + order.getTotalAmount());

        List<OrderItem> orderItems = orderItemDAO.getByOrderId(orderId);

        if (!orderItems.isEmpty()) {
            System.out.println("\nOrder Items:");
            System.out.println("-".repeat(80));
            System.out.printf("%-5s %-40s %-10s %-12s %-12s%n",
                    "ID", "Product", "Quantity", "Unit Price", "Subtotal");
            System.out.println("-".repeat(80));

            for (OrderItem item : orderItems) {
                Product product = productDAO.getById(item.getProductId());
                System.out.printf("%-5d %-40s %-10d $%-11.2f $%-11.2f%n",
                        item.getOrderItemId(),
                        truncate(product.getName(), 40),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal());
            }
            System.out.println("-".repeat(80));
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }
}
