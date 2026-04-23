package controller;

import dao.EventDAO;
import model.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/add-event")
public class AddEventServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read form data
        String eventName = request.getParameter("eventName");
        String description = request.getParameter("description");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");
        String location = request.getParameter("location");

        // Create Event object
        Event event = new Event();
        event.setEventName(eventName);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setLocation(location);

        // Save to database

        boolean added = eventDAO.addEvent(event);

        if (added) {
            request.getSession().setAttribute("successMessage", "Event added successfully!");
        } else {
            request.getSession().setAttribute("failMessage", "Failed to add event. Please try again.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");

    }
}