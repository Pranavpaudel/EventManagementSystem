package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Insert new user (for registration)
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users (full_name, contact, email, password, role, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getContact());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getStatus());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUserByContact(String contact) {

        User user = null;
        String sql = "SELECT * FROM users WHERE contact = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, contact);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setContact(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}