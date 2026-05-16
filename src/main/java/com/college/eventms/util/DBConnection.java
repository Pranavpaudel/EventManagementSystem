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

    /** Returns a new connection to the database, or null if the connection fails. */
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

    /** Verifies the database connection by printing a status message to stdout. */
    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("Database connected");
        } else {
            System.out.println("Database not connected");
        }
    }
}
