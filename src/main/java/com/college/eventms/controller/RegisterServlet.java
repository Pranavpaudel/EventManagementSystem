package com.college.eventms.controller;

import com.college.eventms.dao.UserDAO;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Read data from JSP form
        String fullName = request.getParameter("fullName");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Create User object
        User user = new User();
        user.setFullName(fullName);
        user.setContact(contact);
        user.setEmail(email);
        user.setPassword(password); // later we will encrypt this
        user.setRole("student");
        user.setStatus("pending");

        // 3. Call DAO
        UserDAO userDAO = new UserDAO();
        boolean isRegistered = userDAO.registerUser(user);

        // 4. Redirect based on result
        if (isRegistered) {
            response.sendRedirect(request.getContextPath() + "/register-success.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        }
    }
}