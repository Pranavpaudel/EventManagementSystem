package com.college.eventms.controller;

import com.college.eventms.dao.UserDAO;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setFullName(fullName);
        user.setContact(contact);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("student");
        user.setStatus("pending");

        UserDAO userDAO = new UserDAO();
        boolean isRegistered = userDAO.registerUser(user);

        if (isRegistered) {
            // Redirect to a servlet, not JSP
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")
                    .forward(request, response);
        }
    }
}
