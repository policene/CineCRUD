package com.policene.cinecrud.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class    DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:mydb.db";
    private Connection conn;

    public DatabaseConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Error on connecting to database.", e);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    private void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS movies ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title VARCHAR(255) NOT NULL, "
                + "director VARCHAR(255) NOT NULL, " +
                "year INTEGER NOT NULL, " +
                "rating INTEGER NOT NULL, " +
                "gender VARCHAR(255) NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error on initializing the database.", e);
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error on closing the connection.", e);
        }
    }

}
