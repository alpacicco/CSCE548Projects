package com.ecommerce.db;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility using connection pooling concept.
 * Loads configuration from .env file.
 */
public class DatabaseConnection {
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static boolean initialized = false;

    static {
        initialize();
    }

    /**
     * Initialize database configuration from .env file
     */
    private static void initialize() {
        if (initialized) {
            return;
        }

        try {
            // Load .env file
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();

            String host = dotenv.get("DB_HOST", "localhost");
            String port = dotenv.get("DB_PORT", "3306");
            String database = dotenv.get("DB_NAME", "ecommerce_db");
            DB_USER = dotenv.get("DB_USER", "root");
            DB_PASSWORD = dotenv.get("DB_PASSWORD", "");

            // Build connection URL
            DB_URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    host, port, database);

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            initialized = true;
            System.out.println("Database configuration initialized successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver", e);
        } catch (Exception e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database configuration", e);
        }
    }

    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (!initialized) {
            initialize();
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Test database connection
     * @return true if connection successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Close a connection safely
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
