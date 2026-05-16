package com.college.eventms.controller;

import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet that handles event deletion — admin-only, triggered via GET with an eventId parameter.
 */
@WebServlet("/admin/delete-event")
public class DeleteEventServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        eventDAO.deleteEvent(eventId);

        session.setAttribute("successMessage", "Event deleted successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}