package com.college.eventms.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Utility class for obtaining a JDBC connection to the campus event database.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/campus_event_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Opens and returns a new JDBC connection to the campus event database.
     * Exceptions are caught and logged to stderr; callers must null-check the result.
     *
     * @return a live {@link Connection}, or {@code null} if the driver cannot be loaded
     *         or the database is unreachable
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Smoke-tests the database connection and prints the result to stdout.
     * Intended for quick connectivity checks during development only.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("Database connected");
        } else {
            System.out.println("Database not connected");
        }
    }
}
