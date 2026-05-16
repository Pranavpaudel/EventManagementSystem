package com.college.eventms.controller;

import com.college.eventms.dao.ContactDAO;
import com.college.eventms.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles the contact page — shows the form on GET and saves the submitted message on POST.
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    private final ContactDAO contactDAO = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/contact.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name    = request.getParameter("name");
        String email   = request.getParameter("email");
        String message = request.getParameter("message");

        if (ValidationUtil.isNullOrEmpty(name)
                || ValidationUtil.isNullOrEmpty(email)
                || ValidationUtil.isNullOrEmpty(message)) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/contact.jsp")
                    .forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Please enter a valid email address.");
            request.getRequestDispatcher("/WEB-INF/views/contact.jsp")
                    .forward(request, response);
            return;
        }

        if (contactDAO.saveMessage(name.trim(), email.trim(), message.trim())) {
            request.getSession().setAttribute("successMessage",
                    "Your message has been sent. We'll get back to you soon.");
        } else {
            request.setAttribute("error", "Failed to send message. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/contact.jsp")
                    .forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/contact");
    }
}
