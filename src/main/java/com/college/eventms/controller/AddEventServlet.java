package com.college.eventms.controller;

import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Event;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/add-event")
public class AddEventServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Prevent direct GET access
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Role check (important)
        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String eventName = request.getParameter("eventName");
        String description = request.getParameter("description");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");
        String location = request.getParameter("location");

        Event event = new Event();
        event.setEventName(eventName);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setLocation(location);

        boolean added = eventDAO.addEvent(event);

        if (added) {
            session.setAttribute("successMessage", "Event added successfully!");
        } else {
            session.setAttribute("failMessage", "Failed to add event. Please try again.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}
