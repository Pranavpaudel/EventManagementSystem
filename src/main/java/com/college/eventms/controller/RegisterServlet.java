package com.college.eventms.controller;

import com.college.eventms.service.UserService;
import com.college.eventms.util.ProfileImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Handles new user registration — shows the registration form on GET and creates the account on POST.
 */
@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

    private static final String REGISTER_VIEW = "/WEB-INF/views/auth/register.jsp";
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(REGISTER_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProfileImageUtil.init(getServletContext().getRealPath("/"));
        Part imagePart = request.getPart("profileImage");
        String profileImage = ProfileImageUtil.uploadImage(imagePart);
        if (profileImage == null) {
            profileImage = "static/images/default-avatar.png";
        }

        String error = userService.registerUser(
                request.getParameter("fullName"),
                request.getParameter("contact"),
                request.getParameter("email"),
                request.getParameter("password"),
                profileImage
        );

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher(REGISTER_VIEW).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
