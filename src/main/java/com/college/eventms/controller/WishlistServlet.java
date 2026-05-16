package com.college.eventms.controller;

import com.college.eventms.dao.EventDAO;
import com.college.eventms.entity.Event;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the session-based wishlist — GET shows saved events, POST adds an event to the list.
 */
@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {

    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Integer> wishlist = getWishlist(session);
        List<Event> wishlistEvents = new ArrayList<>();
        for (int eventId : wishlist) {
            Event e = eventDAO.getEventById(eventId);
            if (e != null) wishlistEvents.add(e);
        }

        request.setAttribute("wishlistEvents", wishlistEvents);
        request.getRequestDispatcher("/WEB-INF/views/user/wishlist.jsp")
                .forward(request, response);
    }

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
        List<Integer> wishlist = getWishlist(session);

        if (!wishlist.contains(eventId)) {
            wishlist.add(eventId);
            session.setAttribute("successMessage", "Event saved to your wishlist.");
        } else {
            session.setAttribute("failMessage", "This event is already in your wishlist.");
        }

        response.sendRedirect(request.getContextPath() + "/events");
    }

    @SuppressWarnings("unchecked")
    private List<Integer> getWishlist(HttpSession session) {
        List<Integer> wishlist = (List<Integer>) session.getAttribute("wishlist");
        if (wishlist == null) {
            wishlist = new ArrayList<>();
            session.setAttribute("wishlist", wishlist);
        }
        return wishlist;
    }
}
