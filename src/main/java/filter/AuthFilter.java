package filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")   //  applies filter to ALL requests
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();

        // IMPORTANT: do NOT create a new session
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        //  Public pages (must be allowed)
        if (uri.equals(contextPath + "/") ||
                uri.endsWith("login.jsp") ||
                uri.endsWith("register.jsp") ||
                uri.endsWith("register-success.jsp") ||
                uri.contains("/login") ||
                uri.contains("/register") ||
                uri.contains("/logout") ||
                uri.contains("/css/") ||
                uri.contains("/images/")) {

            chain.doFilter(request, response);
            return;
        }

        // Not logged in → redirect to login
        if (user == null) {
            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // Student trying to access admin pages
        if (uri.contains("admin") &&
                !user.getRole().equalsIgnoreCase("admin")) {

            res.sendRedirect(contextPath + "error.jsp");
            return;
        }

        // Authorized
        chain.doFilter(request, response);
    }
}