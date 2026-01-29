package com.ecommerce;

import com.ecommerce.db.DatabaseConnection;
import com.ecommerce.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting E-Commerce Console Application...");
        System.out.println();

        // Test database connection
        System.out.print("Testing database connection... ");
        if (DatabaseConnection.testConnection()) {
            System.out.println("✓ Connected successfully!");
            System.out.println();

            // Start the console UI
            ConsoleUI ui = new ConsoleUI();
            ui.start();
        } else {
            System.err.println("✗ Failed to connect to database!");
            System.err.println();
            System.err.println("Please check:");
            System.err.println("1. MySQL is running");
            System.err.println("2. Database 'ecommerce_db' exists (run sql/01_schema.sql)");
            System.err.println("3. .env file is configured correctly");
            System.err.println("4. Database credentials are correct");
            System.exit(1);
        }
    }
}
