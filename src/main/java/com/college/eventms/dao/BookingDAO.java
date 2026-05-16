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

    /**
     * Inserts a new booking record linking a user to an event.
     *
     * @param userId  the ID of the user making the booking
     * @param eventId the ID of the event being booked
     * @return {@code true} if the row was inserted successfully, {@code false} otherwise
     */
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

    /**
     * Returns all bookings belonging to the specified user, regardless of status.
     *
     * @param userId the ID of the user whose bookings to fetch
     * @return list of {@link Booking} objects; empty list if the user has no bookings
     */
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

    /**
     * Returns all bookings for the specified event, regardless of status.
     *
     * @param eventId the ID of the event whose bookings to fetch
     * @return list of {@link Booking} objects; empty list if the event has no bookings
     */
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

    /**
     * Sets a booking's status to {@code CANCELLED}.
     *
     * @param bookingId the ID of the booking to cancel
     * @return {@code true} if the update affected at least one row, {@code false} otherwise
     */
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

    /**
     * Returns the number of active (non-cancelled) bookings for the given event.
     * This is an alias of {@link #getActiveBookingCountByEvent(int)} used by JSP EL maps.
     *
     * @param eventId the ID of the event to count bookings for
     * @return the active booking count; {@code 0} on error or if none exist
     */
    public int getBookingCount(int eventId) {
        return getActiveBookingCountByEvent(eventId);
    }

    /**
     * Returns the number of non-cancelled bookings for the given event.
     *
     * @param eventId the ID of the event to count bookings for
     * @return the active booking count; {@code 0} on error or if none exist
     */
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

    /**
     * Returns {@code true} if the user already has an active (non-cancelled) booking for the event.
     * Used to prevent duplicate bookings.
     *
     * @param userId  the ID of the user to check
     * @param eventId the ID of the event to check
     * @return {@code true} if an active booking exists, {@code false} otherwise
     */
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

    /**
     * Returns bookings for the specified user enriched with event details
     * (event name, date, time, and location) via a JOIN, ordered by booking date descending.
     *
     * @param userId the ID of the user whose bookings to fetch
     * @return list of {@link Booking} objects with event fields populated; empty list if none
     */
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
