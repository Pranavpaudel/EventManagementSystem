package com.college.eventms.controller;

import com.college.eventms.dao.UserDAO;
import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Admin authorization check
        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(request, response);
            return;
        }

        List<User> pendingUsers = userDAO.getPendingUsers();
        request.setAttribute("pendingUsers", pendingUsers);

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-users.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Admin authorization check
        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.approveUser(userId);

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}