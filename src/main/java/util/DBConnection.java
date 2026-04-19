package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/campus_event_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // This is the method UserDAO needs
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

    // Optional: keep this just for testing
    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("Database connected");
        } else {
            System.out.println("Database not connected");
        }
    }
}
