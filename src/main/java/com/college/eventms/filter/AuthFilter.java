package com.college.eventms.filter;

import com.college.eventms.controller.LoginServlet;
import com.college.eventms.entity.User;
import com.college.eventms.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Global authentication filter applied to all URLs — redirects unauthenticated users to /login,
 * auto-restores sessions from the remember-me cookie, and blocks unauthorised role access.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    private final UserService userService = new UserService();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String ctx = request.getContextPath();

        // Public routes and static files
        if (path.contains("/login")
                || path.contains("/register")
                || path.contains("/contact")
                || path.contains("/about")
                || path.contains("/static/")
                || path.contains("/event-uploads/")
                || path.contains("/profile-uploads/")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".png")
                || path.endsWith(".jpg")
                || path.endsWith(".svg")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // No active session — check for remember-me cookie and auto-login
        if (user == null) {
            user = restoreFromCookie(request);
            if (user != null) {
                request.getSession(true).setAttribute("user", user);
            }
        }

        // Not logged in
        if (user == null) {
            response.sendRedirect(ctx + "/login");
            return;
        }

        // Admin & Co-Admin allowed for admin routes
        if (path.startsWith(ctx + "/admin")
                && !user.getRole().equalsIgnoreCase("admin")
                && !user.getRole().equalsIgnoreCase("co-admin")) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        // User dashboard allowed ONLY for students
        if (path.startsWith(ctx + "/user-dashboard")
                && !user.getRole().equalsIgnoreCase("student")) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Looks for the remember-me cookie in the request, and if found, fetches the matching
     * approved user. Returns {@code null} if the cookie is absent, empty, or the account
     * is no longer approved.
     */
    private User restoreFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (LoginServlet.REMEMBER_COOKIE.equals(cookie.getName())) {
                String identifier = cookie.getValue();
                if (identifier == null || identifier.isEmpty()) return null;

                User user = userService.findByIdentifier(identifier);
                if (user != null && user.getStatus().equalsIgnoreCase("approved")) {
                    return user;
                }
                return null;
            }
        }
        return null;
    }
}