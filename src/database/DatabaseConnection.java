package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/burger_machine";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Change if you have a password

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Connect to the database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected!");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("❌ Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }
}
