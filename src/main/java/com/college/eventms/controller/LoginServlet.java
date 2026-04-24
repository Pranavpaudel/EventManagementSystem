package com.college.eventms.controller;

import com.college.eventms.dao.UserDAO;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        // Try contact first, then email
        User user = userDAO.getUserByContact(identifier);
        if (user == null) {
            user = userDAO.getUserByEmail(identifier);
        }

        if (user == null) {
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
                    .forward(request, response);
            return;
        }

        if (!user.getPassword().equals(password)) {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
                    .forward(request, response);
            return;
        }

        if (!user.getStatus().equalsIgnoreCase("approved")) {
            request.setAttribute("error", "Account not approved by admin");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
                    .forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);


        if (user.getRole().equalsIgnoreCase("admin")
                || user.getRole().equalsIgnoreCase("co-admin")) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/user-dashboard");
        }
    }
}
