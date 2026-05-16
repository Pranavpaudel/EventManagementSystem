package com.college.eventms.controller;

import com.college.eventms.entity.User;
import com.college.eventms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles user authentication — shows the login form on GET and processes credentials on POST.
 * When "Remember Me" is checked, sets a 30-day {@code remember_identifier} cookie so the
 * AuthFilter can restore the session on future visits without re-entering credentials.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    static final String REMEMBER_COOKIE = "remember_identifier";
    private static final int  THIRTY_DAYS = 30 * 24 * 60 * 60;

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String identifier = request.getParameter("identifier");
        String password   = request.getParameter("password");

        User found = userService.findByIdentifier(identifier);
        if (found == null) {
            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        if (!userService.verifyPassword(password, found.getPassword())) {
            request.setAttribute("error", "Invalid credentials.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        if (!found.getStatus().equalsIgnoreCase("approved")) {
            request.setAttribute("error", "Account not approved by admin.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", found);

        boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
        Cookie cookie = new Cookie(REMEMBER_COOKIE, rememberMe ? identifier : "");
        cookie.setMaxAge(rememberMe ? THIRTY_DAYS : 0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        if (found.getRole().equalsIgnoreCase("admin")
                || found.getRole().equalsIgnoreCase("co-admin")) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/user-dashboard");
        }
    }
}
