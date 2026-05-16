package com.college.eventms.controller;

import com.college.eventms.dao.BookingDAO;
import com.college.eventms.entity.Booking;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Displays the current user's booking history, including event details for each booking.
 */
@WebServlet("/my-bookings")
public class MyBookingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getBookingsByUserWithEvent(user.getUserId());

        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/WEB-INF/views/user/my-bookings.jsp")
                .forward(request, response);
    }
}
