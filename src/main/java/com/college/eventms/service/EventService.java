package com.college.eventms.service;

import com.college.eventms.dao.CategoryDAO;
import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Category;
import com.college.eventms.entity.Event;
import com.college.eventms.util.DateUtil;
import com.college.eventms.util.ValidationUtil;

import java.util.List;

/**
 * Service layer for event-related business logic — validation, creation, and updates.
 */
public class EventService {

    private final EventDAO eventDAO = new EventDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Validates and creates a new event.
     *
     * @return null on success, or a human-readable error message on failure.
     */
    public String createEvent(Event event) {
        if (ValidationUtil.isNullOrEmpty(event.getEventName())) {
            return "Event name is required.";
        }
        if (event.getCapacity() < 1) {
            return "Capacity must be at least 1.";
        }
        if (DateUtil.isPastDate(event.getEventDate())) {
            return "Event date cannot be in the past.";
        }
        return eventDAO.addEvent(event) ? null : "Failed to add event.";
    }

    /**
     * Validates and updates an existing event.
     *
     * @return null on success, or a human-readable error message on failure.
     */
    public String updateEvent(Event event) {
        if (ValidationUtil.isNullOrEmpty(event.getEventName())) {
            return "Event name is required.";
        }
        if (event.getCapacity() < 1) {
            return "Capacity must be at least 1.";
        }
        return eventDAO.updateEvent(event) ? null : "Failed to update event.";
    }

    /** Deletes the event with the given ID. */
    public boolean deleteEvent(int eventId)                     { return eventDAO.deleteEvent(eventId); }

    /** Returns the event with the given ID, or null if not found. */
    public Event getEventById(int eventId)                      { return eventDAO.getEventById(eventId); }

    /** Returns all events in the system. */
    public List<Event> getAllEvents()                            { return eventDAO.getAllEvents(); }

    /** Returns upcoming events (today or later). */
    public List<Event> getUpcomingEvents()                      { return eventDAO.getUpcomingEvents(); }

    /** Returns events whose date is before today. */
    public List<Event> getPastEvents()                          { return eventDAO.getPastEvents(); }

    /** Searches upcoming events by keyword, category ID, and/or date range; any parameter may be omitted. */
    public List<Event> searchEvents(String k, int cId, String dateFrom, String dateTo){ return eventDAO.searchEvents(k, cId, dateFrom, dateTo); }

    /** Returns all available event categories. */
    public List<Category> getAllCategories()                    { return categoryDAO.getAllCategories(); }
}
