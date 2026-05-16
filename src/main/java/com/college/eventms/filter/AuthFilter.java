package com.college.eventms.filter;

import com.college.eventms.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Global authentication filter applied to all URLs — redirects unauthenticated users to /login
 * and blocks unauthorised role access to /admin/* and /user-dashboard.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

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
}