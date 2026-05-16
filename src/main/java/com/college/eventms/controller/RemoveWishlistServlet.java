package com.college.eventms.controller;

import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Removes a single event from the user's session-based wishlist.
 */
@WebServlet("/wishlist/remove")
public class RemoveWishlistServlet extends HttpServlet {

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

        @SuppressWarnings("unchecked")
        List<Integer> wishlist = (List<Integer>) session.getAttribute("wishlist");
        if (wishlist != null) {
            wishlist.remove(Integer.valueOf(eventId));
        }

        response.sendRedirect(request.getContextPath() + "/wishlist");
    }
}
