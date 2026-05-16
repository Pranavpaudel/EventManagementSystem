package com.college.eventms.dao;

import com.college.eventms.entity.Booking;
import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the bookings table — handles booking creation, cancellation, and queries.
 */
public class BookingDAO {

    /** Inserts a new booking for the given user and event; returns true on success. */
    public boolean insertBooking(int userId, int eventId) {

        String sql = "INSERT INTO bookings (user_id, event_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Returns all bookings belonging to the specified user. */
    public List<Booking> getBookingsByUser(int userId) {

        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    /** Returns all bookings for the specified event. */
    public List<Booking> getBookingsByEvent(int eventId) {

        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    /** Sets a booking's status to CANCELLED; returns true on success. */
    public boolean cancelBooking(int bookingId) {

        String sql = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Returns the number of non-cancelled bookings for the given event (alias used by JSP maps). */
    public int getBookingCount(int eventId) {
        return getActiveBookingCountByEvent(eventId);
    }

    /** Returns the number of non-cancelled bookings for the given event. */
    public int getActiveBookingCountByEvent(int eventId) {

        String sql = "SELECT COUNT(*) FROM bookings WHERE event_id = ? AND status != 'CANCELLED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Returns true if the user has an active (non-cancelled) booking for the event. */
    public boolean isAlreadyBooked(int userId, int eventId) {

        String sql = "SELECT COUNT(*) FROM bookings WHERE user_id = ? AND event_id = ? AND status != 'CANCELLED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Returns bookings for the specified user joined with event details (name, date, time, location). */
    public List<Booking> getBookingsByUserWithEvent(int userId) {

        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, e.event_name, e.event_date, e.event_time, e.location " +
                "FROM bookings b JOIN events e ON b.event_id = e.event_id " +
                "WHERE b.user_id = ? ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = mapRow(rs);
                booking.setEventName(rs.getString("event_name"));
                booking.setEventDate(rs.getString("event_date"));
                booking.setEventTime(rs.getString("event_time"));
                booking.setLocation(rs.getString("location"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setEventId(rs.getInt("event_id"));
        booking.setBookingDate(rs.getString("booking_date"));
        booking.setStatus(rs.getString("status"));
        return booking;
    }
}
