package com.college.eventms.controller;

import com.college.eventms.dao.BookingDAO;
import com.college.eventms.dao.CategoryDAO;
import com.college.eventms.dao.EventDAO;
import com.college.eventms.dao.ReportDAO;
import com.college.eventms.entity.Category;
import com.college.eventms.entity.Event;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet that serves the admin dashboard — loads all events and summary statistics for display.
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();
    private final ReportDAO reportDAO = new ReportDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null
                || (!user.getRole().equalsIgnoreCase("admin")
                && !user.getRole().equalsIgnoreCase("co-admin"))) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        List<Event> events = eventDAO.getAllEvents();

        Map<Integer, String> categoryNames = new HashMap<>();
        for (Category cat : categoryDAO.getAllCategories()) {
            categoryNames.put(cat.getCategoryId(), cat.getName());
        }

        Map<Integer, Integer> bookingCounts = new HashMap<>();
        for (Event event : events) {
            bookingCounts.put(event.getEventId(), bookingDAO.getBookingCount(event.getEventId()));
        }

        request.setAttribute("events", events);
        request.setAttribute("categoryNames", categoryNames);
        request.setAttribute("bookingCounts", bookingCounts);
        request.setAttribute("stats", reportDAO.getSummaryStats());

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-dashboard.jsp")
                .forward(request, response);
    }
}
