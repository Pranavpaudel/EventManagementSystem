package com.college.eventms.filter;

import com.college.eventms.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Secondary filter for admin routes — ensures only admin and co-admin users can access /admin/* URLs.
 */
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

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

        chain.doFilter(request, response);
    }
}
