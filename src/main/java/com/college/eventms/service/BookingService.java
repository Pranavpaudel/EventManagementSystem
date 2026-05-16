package com.college.eventms.service;

import com.college.eventms.dao.BookingDAO;
import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Booking;
import com.college.eventms.entity.Event;

import java.util.List;

/**
 * Service layer for booking business logic — enforces capacity limits and prevents duplicate bookings.
 */
public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final EventDAO eventDAO = new EventDAO();

    /**
     * Attempts to book an event for the given user after checking for duplicates and capacity.
     *
     * @return null on success, or a human-readable error message if the booking cannot be made.
     */
    public String bookEvent(int userId, int eventId) {
        if (bookingDAO.isAlreadyBooked(userId, eventId)) {
            return "You have already registered for this event.";
        }

        Event event = eventDAO.getEventById(eventId);
        if (event != null && bookingDAO.getActiveBookingCountByEvent(eventId) >= event.getCapacity()) {
            return "This event is fully booked.";
        }

        return bookingDAO.insertBooking(userId, eventId) ? null : "Registration failed. Please try again.";
    }

    /** Cancels the booking with the given ID. */
    public boolean cancelBooking(int bookingId)                         { return bookingDAO.cancelBooking(bookingId); }

    /** Returns true if the user has an active booking for the event. */
    public boolean isAlreadyBooked(int userId, int eventId)             { return bookingDAO.isAlreadyBooked(userId, eventId); }

    /** Returns all bookings for the given user. */
    public List<Booking> getBookingsByUser(int userId)                  { return bookingDAO.getBookingsByUser(userId); }

    /** Returns bookings for the given user including joined event display fields. */
    public List<Booking> getBookingsByUserWithEvent(int userId)         { return bookingDAO.getBookingsByUserWithEvent(userId); }
}
