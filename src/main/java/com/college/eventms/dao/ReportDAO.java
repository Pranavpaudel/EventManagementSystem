package com.college.eventms.dao;

import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for reporting queries — returns aggregated data as Maps for flexible JSP rendering.
 */
public class ReportDAO {

    /**
     * Returns per-event attendance: event details plus total non-cancelled booking count,
     * ordered by bookings descending.
     */
    public List<Map<String, Object>> getAttendanceReport() {

        List<Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT e.event_id, e.event_name, e.event_date, e.capacity, " +
                "COUNT(b.booking_id) AS total_bookings " +
                "FROM events e LEFT JOIN bookings b " +
                "ON e.event_id = b.event_id AND b.status != 'CANCELLED' " +
                "GROUP BY e.event_id ORDER BY total_bookings DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("eventId",       rs.getInt("event_id"));
                row.put("eventName",     rs.getString("event_name"));
                row.put("eventDate",     rs.getString("event_date"));
                row.put("capacity",      rs.getInt("capacity"));
                row.put("totalBookings", rs.getInt("total_bookings"));
                rows.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    /** Returns the number of events per category, ordered by count descending. */
    public List<Map<String, Object>> getCategoryBreakdown() {

        List<Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT c.name AS category_name, COUNT(e.event_id) AS event_count " +
                "FROM categories c LEFT JOIN events e ON c.category_id = e.category_id " +
                "GROUP BY c.category_id ORDER BY event_count DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("categoryName", rs.getString("category_name"));
                row.put("eventCount",   rs.getInt("event_count"));
                rows.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    /** Returns a single-row summary: totalUsers, totalEvents, totalBookings, pendingUsers. */
    public Map<String, Object> getSummaryStats() {

        Map<String, Object> stats = new HashMap<>();
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM users) AS total_users, " +
                "(SELECT COUNT(*) FROM events) AS total_events, " +
                "(SELECT COUNT(*) FROM bookings WHERE status != 'CANCELLED') AS total_bookings, " +
                "(SELECT COUNT(*) FROM users WHERE status = 'pending') AS pending_users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                stats.put("totalUsers",    rs.getInt("total_users"));
                stats.put("totalEvents",   rs.getInt("total_events"));
                stats.put("totalBookings", rs.getInt("total_bookings"));
                stats.put("pendingUsers",  rs.getInt("pending_users"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
}
