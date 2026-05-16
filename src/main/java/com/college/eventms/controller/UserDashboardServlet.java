package com.college.eventms.controller;

import com.college.eventms.dao.BookingDAO;
import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Booking;
import com.college.eventms.entity.Event;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet that serves the student dashboard — displays booking counts and upcoming events.
 */
@WebServlet("/user-dashboard")
public class UserDashboardServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("student")) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        List<Booking> myBookings = bookingDAO.getBookingsByUser(user.getUserId());

        int confirmedCount = 0;
        int cancelledCount = 0;
        for (Booking b : myBookings) {
            if ("CANCELLED".equalsIgnoreCase(b.getStatus())) cancelledCount++;
            else confirmedCount++;
        }

        List<Event> upcomingEvents = eventDAO.getUpcomingEvents();

        List<Integer> bookedEventIds = new ArrayList<>();
        for (Booking b : myBookings) {
            if (!"CANCELLED".equalsIgnoreCase(b.getStatus())) {
                bookedEventIds.add(b.getEventId());
            }
        }

        Map<Integer, Integer> bookingCounts = new HashMap<>();
        for (Event e : upcomingEvents) {
            bookingCounts.put(e.getEventId(), bookingDAO.getBookingCount(e.getEventId()));
        }

        request.setAttribute("confirmedCount",  confirmedCount);
        request.setAttribute("cancelledCount",  cancelledCount);
        request.setAttribute("upcomingEvents",  upcomingEvents);
        request.setAttribute("myBookings",      myBookings);
        request.setAttribute("bookedEventIds",  bookedEventIds);
        request.setAttribute("bookingCounts",   bookingCounts);

        request.getRequestDispatcher("/WEB-INF/views/user/user-dashboard.jsp")
                .forward(request, response);
    }
}
