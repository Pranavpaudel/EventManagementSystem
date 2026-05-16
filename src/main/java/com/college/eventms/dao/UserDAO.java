package com.college.eventms.dao;

import com.college.eventms.entity.User;
import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the users table — provides CRUD operations and role management.
 */
public class UserDAO {

    /**
     * Inserts a new user record into the database.
     *
     * @param user the {@link User} to persist; password must already be hashed
     * @return {@code true} if the row was inserted successfully, {@code false} otherwise
     */
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users (full_name, contact, email, password, role, status, profile_image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getContact());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getStatus());
            ps.setString(7, user.getProfileImage() != null ? user.getProfileImage() : "static/images/default-avatar.png");

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Looks up a user by their contact number.
     *
     * @param contact the contact number to search for
     * @return the matching {@link User}, or {@code null} if no record exists
     * @throws SQLException if the database query fails
     */
    public User getUserByContact(String contact) throws SQLException {

        String sql = "SELECT * FROM users WHERE contact = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, contact);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setContact(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setProfileImage(rs.getString("profile_image"));
                return user;
            }
        }
        return null;
    }

    /**
     * Looks up a user by their email address.
     *
     * @param email the email address to search for
     * @return the matching {@link User}, or {@code null} if no record exists
     * @throws SQLException if the database query fails
     */
    public User getUserByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setContact(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setProfileImage(rs.getString("profile_image"));
                return user;
            }
        }
        return null;
    }

    /**
     * Returns all users whose account status is {@code pending} (awaiting admin approval).
     *
     * @return list of pending {@link User} objects; empty list if none
     */
    public List<User> getPendingUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE status = 'pending'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setContact(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setProfileImage(rs.getString("profile_image"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Sets a user's status to {@code approved}, granting login access.
     *
     * @param userId the ID of the user to approve
     * @return {@code true} if the update affected at least one row, {@code false} otherwise
     */
    public boolean approveUser(int userId) {

        String sql = "UPDATE users SET status = 'approved' WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns all users in the system ordered by {@code user_id} ascending.
     *
     * @return list of all {@link User} objects; empty list if the table is empty
     */
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setContact(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setProfileImage(rs.getString("profile_image"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Permanently deletes a user record by ID. This action is irreversible.
     *
     * @param userId the ID of the user to delete
     * @return {@code true} if the row was deleted, {@code false} if no row matched
     */
    public boolean deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates a user's full name, contact number, email, and profile image.
     *
     * @param user the {@link User} containing updated field values; {@code userId} must be set
     * @return {@code true} if the update succeeded, {@code false} otherwise
     */
    public boolean updateUser(User user) {

        String sql = "UPDATE users SET full_name = ?, contact = ?, email = ?, profile_image = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getContact());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getProfileImage() != null ? user.getProfileImage() : "static/images/default-avatar.png");
            ps.setInt(5, user.getUserId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Changes the role of the specified user.
     *
     * @param userId  the ID of the user whose role should change
     * @param newRole the new role string (e.g. {@code "admin"}, {@code "co-admin"}, {@code "student"})
     * @return {@code true} if the update succeeded, {@code false} otherwise
     */
    public boolean updateUserRole(int userId, String newRole) {

        String sql = "UPDATE users SET role = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newRole);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
