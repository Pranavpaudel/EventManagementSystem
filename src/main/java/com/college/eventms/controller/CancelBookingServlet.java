package com.college.eventms.controller;

import com.college.eventms.dao.BookingDAO;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles booking cancellation requests — marks the specified booking as CANCELLED.
 */
@WebServlet("/cancel-booking")
public class CancelBookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        BookingDAO bookingDAO = new BookingDAO();

        if (bookingDAO.cancelBooking(bookingId)) {
            session.setAttribute("successMessage", "Your booking has been cancelled.");
        } else {
            session.setAttribute("failMessage", "Could not cancel booking. Please try again.");
        }

        response.sendRedirect(request.getContextPath() + "/my-bookings");
    }
}
