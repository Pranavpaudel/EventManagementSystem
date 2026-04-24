package com.college.eventms.dao;

import com.college.eventms.entity.Event;
import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    /* =========================
       ADD EVENT (ADMIN)
       ========================= */
    public boolean addEvent(Event event) {

        String sql = "INSERT INTO events (event_name, description, event_date, event_time, location) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getEventDate());
            ps.setString(4, event.getEventTime());
            ps.setString(5, event.getLocation());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       VIEW ALL EVENTS
       ========================= */
    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setEventName(rs.getString("event_name"));
                event.setDescription(rs.getString("description"));
                event.setEventDate(rs.getString("event_date"));
                event.setEventTime(rs.getString("event_time"));
                event.setLocation(rs.getString("location"));

                events.add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public Event getEventById(int eventId) {

        String sql = "SELECT * FROM events WHERE event_id = ?";
        Event event = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setEventName(rs.getString("event_name"));
                event.setDescription(rs.getString("description"));
                event.setEventDate(rs.getString("event_date"));
                event.setEventTime(rs.getString("event_time"));
                event.setLocation(rs.getString("location"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }

    public boolean updateEvent(Event event) {

        String sql = "UPDATE events SET event_name = ?, description = ?, event_date = ?, event_time = ?, location = ? WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getEventDate());
            ps.setString(4, event.getEventTime());
            ps.setString(5, event.getLocation());
            ps.setInt(6, event.getEventId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
