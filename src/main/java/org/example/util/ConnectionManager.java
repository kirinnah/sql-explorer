package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5433/university");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "user");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "12345");
    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}