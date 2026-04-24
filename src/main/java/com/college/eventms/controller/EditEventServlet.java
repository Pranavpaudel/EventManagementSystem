package com.college.eventms.controller;

import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Event;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/edit-event")
public class EditEventServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();

    // Show edit form (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(request, response);
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventDAO.getEventById(eventId);

        request.setAttribute("event", event);
        request.getRequestDispatcher("/WEB-INF/views/admin/edit-event.jsp")
                .forward(request, response);
    }

    // Process form (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Event event = new Event();
        event.setEventId(Integer.parseInt(request.getParameter("eventId")));
        event.setEventName(request.getParameter("eventName"));
        event.setDescription(request.getParameter("description"));
        event.setEventDate(request.getParameter("eventDate"));
        event.setEventTime(request.getParameter("eventTime"));
        event.setLocation(request.getParameter("location"));

        eventDAO.updateEvent(event);

        session.setAttribute("successMessage", "Event updated successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}