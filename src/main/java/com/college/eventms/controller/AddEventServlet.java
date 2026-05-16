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
 * Servlet for adding a new event — shows the form on GET and persists the event on POST.
 */
@WebServlet("/admin/add-event")
@MultipartConfig
public class AddEventServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!user.getRole().equalsIgnoreCase("admin")
                && !user.getRole().equalsIgnoreCase("co-admin")) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("categories", categoryDAO.getAllCategories());
        request.getRequestDispatcher("/WEB-INF/views/admin/add-event.jsp")
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

        String eventName    = request.getParameter("eventName");
        String description  = request.getParameter("description");
        String eventDate    = request.getParameter("eventDate");
        String eventTime    = request.getParameter("eventTime");
        String location     = request.getParameter("location");
        String capacityStr  = request.getParameter("capacity");
        String categoryIdStr = request.getParameter("categoryId");

        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            capacity = 0;
        }

        if (capacity < 1) {
            request.setAttribute("error", "Capacity must be at least 1.");
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/views/admin/add-event.jsp")
                    .forward(request, response);
            return;
        }

        Part imagePart = request.getPart("image");
        String imagePath = ImageUtil.uploadImage(imagePart);
        if (imagePath == null) {
            imagePath = "static/images/default-event.png";
        }

        Event event = new Event();
        event.setEventName(eventName);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setLocation(location);
        event.setImage(imagePath);
        event.setCapacity(capacity);
        event.setCategoryId(Integer.parseInt(categoryIdStr));
        event.setStatus("UPCOMING");
        event.setCreatedBy(user.getUserId());

        boolean added = eventDAO.addEvent(event);

        if (added) {
            session.setAttribute("successMessage", "Event added successfully!");
        } else {
            session.setAttribute("failMessage", "Failed to add event.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}
