package com.college.eventms.controller;

import com.college.eventms.dao.CategoryDAO;
import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Event;
import com.college.eventms.entity.User;
import com.college.eventms.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet for editing an existing event — shows the pre-filled form on GET and saves changes on POST.
 */
@WebServlet("/admin/edit-event")
@MultipartConfig
public class EditEventServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventDAO.getEventById(eventId);

        request.setAttribute("event", event);
        request.setAttribute("categories", categoryDAO.getAllCategories());
        request.getRequestDispatcher("/WEB-INF/views/admin/edit-event.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String capacityStr   = request.getParameter("capacity");
        String categoryIdStr = request.getParameter("categoryId");
        int eventId = Integer.parseInt(request.getParameter("eventId"));

        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            capacity = 0;
        }

        if (capacity < 1) {
            request.setAttribute("error", "Capacity must be at least 1.");
            request.setAttribute("event", eventDAO.getEventById(eventId));
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-event.jsp")
                    .forward(request, response);
            return;
        }

        Event existing = eventDAO.getEventById(eventId);
        Part imagePart = request.getPart("image");
        String newImagePath = ImageUtil.uploadImage(imagePart);
        String imagePath;
        if (newImagePath != null) {
            ImageUtil.deleteImage(existing != null ? existing.getImage() : null);
            imagePath = newImagePath;
        } else {
            imagePath = existing != null ? existing.getImage() : "static/images/default-event.png";
        }

        Event event = new Event();
        event.setEventId(eventId);
        event.setEventName(request.getParameter("eventName"));
        event.setDescription(request.getParameter("description"));
        event.setEventDate(request.getParameter("eventDate"));
        event.setEventTime(request.getParameter("eventTime"));
        event.setLocation(request.getParameter("location"));
        event.setImage(imagePath);
        event.setCapacity(capacity);
        event.setCategoryId(Integer.parseInt(categoryIdStr));
        event.setStatus(request.getParameter("status"));
        event.setCreatedBy(Integer.parseInt(request.getParameter("createdBy")));

        eventDAO.updateEvent(event);

        session.setAttribute("successMessage", "Event updated successfully!");
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}
