package com.college.eventms.dao;

import com.college.eventms.entity.ContactMessage;
import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the contact_messages table — handles saving and retrieving contact form submissions.
 */
public class ContactDAO {

    /** Saves a contact form submission to the database; returns true on success. */
    public boolean saveMessage(String name, String email, String message) {

        String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, message);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Returns all contact messages ordered by submission date descending. */
    public List<ContactMessage> getAllMessages() {

        List<ContactMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM contact_messages ORDER BY submitted_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ContactMessage msg = new ContactMessage();
                msg.setMessageId(rs.getInt("message_id"));
                msg.setName(rs.getString("name"));
                msg.setEmail(rs.getString("email"));
                msg.setMessage(rs.getString("message"));
                msg.setSubmittedAt(rs.getString("submitted_at"));
                messages.add(msg);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
