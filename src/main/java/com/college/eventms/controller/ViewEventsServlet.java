package com.college.eventms.controller;

import com.college.eventms.dao.BookingDAO;
import com.college.eventms.dao.CategoryDAO;
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
 * Displays upcoming events for logged-in students with optional keyword, category, and date filters.
 */
@WebServlet("/events")
public class ViewEventsServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String keyword       = request.getParameter("keyword");
        String categoryIdStr = request.getParameter("categoryId");
        String dateFrom      = request.getParameter("dateFrom");
        String dateTo        = request.getParameter("dateTo");

        int categoryId = 0;
        try {
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr);
            }
        } catch (NumberFormatException ignored) {}

        boolean hasFilter = (keyword != null && !keyword.trim().isEmpty())
                || categoryId > 0
                || (dateFrom != null && !dateFrom.trim().isEmpty())
                || (dateTo != null && !dateTo.trim().isEmpty());

        List<Event> events = hasFilter
                ? eventDAO.searchEvents(keyword, categoryId, dateFrom, dateTo)
                : eventDAO.getUpcomingEvents();

        List<Integer> bookedEventIds = new ArrayList<>();
        for (Booking b : bookingDAO.getBookingsByUser(user.getUserId())) {
            if (!"CANCELLED".equalsIgnoreCase(b.getStatus())) {
                bookedEventIds.add(b.getEventId());
            }
        }

        Map<Integer, Integer> bookingCounts = new HashMap<>();
        for (Event e : events) {
            bookingCounts.put(e.getEventId(), bookingDAO.getBookingCount(e.getEventId()));
        }

        request.setAttribute("events", events);
        request.setAttribute("categories", categoryDAO.getAllCategories());
        request.setAttribute("keyword", keyword != null ? keyword : "");
        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute("selectedDateFrom", dateFrom != null ? dateFrom : "");
        request.setAttribute("selectedDateTo", dateTo != null ? dateTo : "");
        request.setAttribute("bookedEventIds", bookedEventIds);
        request.setAttribute("bookingCounts", bookingCounts);

        request.getRequestDispatcher("/WEB-INF/views/user/view-events.jsp")
                .forward(request, response);
    }
}
