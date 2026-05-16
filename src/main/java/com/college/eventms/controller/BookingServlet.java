package com.college.eventms.controller;

import com.college.eventms.entity.User;
import com.college.eventms.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles event booking requests from students — validates eligibility and registers the user for an event.
 */
@WebServlet("/book-event")
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String error = bookingService.bookEvent(user.getUserId(), eventId);

        if (error != null) {
            session.setAttribute("failMessage", error);
        } else {
            session.setAttribute("successMessage", "You have successfully registered for the event.");
        }

        response.sendRedirect(request.getContextPath() + "/events");
    }
}
