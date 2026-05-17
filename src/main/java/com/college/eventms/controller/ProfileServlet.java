package com.college.eventms.controller;

import com.college.eventms.dao.UserDAO;
import com.college.eventms.entity.User;
import com.college.eventms.util.ProfileImageUtil;
import com.college.eventms.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles the user profile page — shows profile details on GET and saves updates on POST.
 */
@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp")
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

        String fullName = request.getParameter("fullName");
        String contact  = request.getParameter("contact");
        String email    = request.getParameter("email");

        if (ValidationUtil.isNullOrEmpty(fullName)
                || ValidationUtil.isNullOrEmpty(contact)
                || ValidationUtil.isNullOrEmpty(email)) {
            session.setAttribute("failMessage", "All fields are required.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        if (!ValidationUtil.isAlphaOnly(fullName)) {
            session.setAttribute("failMessage", "Name must contain only letters and spaces.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            session.setAttribute("failMessage", "Please enter a valid email address.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        if (!ValidationUtil.isValidPhone(contact)) {
            session.setAttribute("failMessage", "Phone number must be 7–15 digits.");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        ProfileImageUtil.init(getServletContext().getRealPath("/"));
        Part imagePart = request.getPart("profileImage");
        String newImagePath = ProfileImageUtil.uploadImage(imagePart);
        if (newImagePath != null) {
            ProfileImageUtil.deleteImage(user.getProfileImage());
            user.setProfileImage(newImagePath);
        }

        user.setFullName(fullName.trim());
        user.setContact(contact.trim());
        user.setEmail(email.trim());

        if (userDAO.updateUser(user)) {
            session.setAttribute("user", user);
            session.setAttribute("successMessage", "Profile updated successfully.");
        } else {
            session.setAttribute("failMessage", "Update failed. Please try again.");
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
