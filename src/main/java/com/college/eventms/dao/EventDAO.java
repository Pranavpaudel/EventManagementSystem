package com.college.eventms.dao;

import com.college.eventms.entity.Event;
import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the events table — provides CRUD, search, and filter queries.
 */
public class EventDAO {

    /** Inserts a new event record into the database; returns true on success. */
    public boolean addEvent(Event event) {

        String sql = "INSERT INTO events (event_name, description, event_date, event_time, location, image, capacity, category_id, status, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getEventDate());
            ps.setString(4, event.getEventTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getImage() != null ? event.getImage() : "static/images/default-event.png");
            ps.setInt(7, event.getCapacity());
            ps.setInt(8, event.getCategoryId());
            ps.setString(9, event.getStatus());
            ps.setInt(10, event.getCreatedBy());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Returns all events in the database. */
    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                events.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    /** Returns the event with the given ID, or null if not found. */
    public Event getEventById(int eventId) {

        String sql = "SELECT * FROM events WHERE event_id = ?";
        Event event = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                event = mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }

    /** Updates all fields of an existing event record; returns true on success. */
    public boolean updateEvent(Event event) {

        String sql = "UPDATE events SET event_name = ?, description = ?, event_date = ?, event_time = ?, " +
                "location = ?, image = ?, capacity = ?, category_id = ?, status = ?, created_by = ? " +
                "WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getEventDate());
            ps.setString(4, event.getEventTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getImage() != null ? event.getImage() : "static/images/default-event.png");
            ps.setInt(7, event.getCapacity());
            ps.setInt(8, event.getCategoryId());
            ps.setString(9, event.getStatus());
            ps.setInt(10, event.getCreatedBy());
            ps.setInt(11, event.getEventId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Permanently removes an event record by ID; returns true on success. */
    public boolean deleteEvent(int eventId) {

        String sql = "DELETE FROM events WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Returns all events whose date is today or in the future, ordered by date ascending. */
    public List<Event> getUpcomingEvents() {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_date >= CURRENT_DATE ORDER BY event_date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                events.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    /** Returns all events whose date is before today, ordered by date descending. */
    public List<Event> getPastEvents() {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_date < CURRENT_DATE ORDER BY event_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                events.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    /**
     * Searches upcoming events by keyword, category, and/or date range.
     * Any parameter may be omitted (null/empty/0).
     */
    public List<Event> searchEvents(String keyword, int categoryId, String dateFrom, String dateTo) {

        List<Event> events = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM events WHERE 1=1 AND event_date >= CURRENT_DATE");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (event_name LIKE ? OR description LIKE ? OR location LIKE ?)");
            String like = "%" + keyword + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (categoryId > 0) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }
        if (dateFrom != null && !dateFrom.trim().isEmpty()) {
            sql.append(" AND event_date >= ?");
            params.add(dateFrom);
        }
        if (dateTo != null && !dateTo.trim().isEmpty()) {
            sql.append(" AND event_date <= ?");
            params.add(dateTo);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof Integer) ps.setInt(i + 1, (Integer) p);
                else ps.setString(i + 1, (String) p);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                events.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    /** Searches events by keyword against name, description, and location fields. */
    public List<Event> searchEvents(String keyword) {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_name LIKE ? OR description LIKE ? OR location LIKE ?";
        String param = "%" + keyword + "%";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, param);
            ps.setString(2, param);
            ps.setString(3, param);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                events.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    private Event mapRow(ResultSet rs) throws Exception {
        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setEventName(rs.getString("event_name"));
        event.setDescription(rs.getString("description"));
        event.setEventDate(rs.getString("event_date"));
        event.setEventTime(rs.getString("event_time"));
        event.setLocation(rs.getString("location"));
        event.setImage(rs.getString("image"));
        event.setCapacity(rs.getInt("capacity"));
        event.setCategoryId(rs.getInt("category_id"));
        event.setStatus(rs.getString("status"));
        event.setCreatedBy(rs.getInt("created_by"));
        return event;
    }
}
