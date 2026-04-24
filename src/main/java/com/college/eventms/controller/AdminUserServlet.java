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

        // Admin or Co-Admin authorization check
        if (user == null || (!user.getRole().equalsIgnoreCase("admin")
                && !user.getRole().equalsIgnoreCase("co-admin"))) {
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(request, response);
            return;
        }

        // Load ALL users (pending + approved)
        List<User> allUsers = userDAO.getAllUsers();
        request.setAttribute("allUsers", allUsers);

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-users.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        // Admin or Co-Admin authorization check
        if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("admin")
                && !currentUser.getRole().equalsIgnoreCase("co-admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        int targetUserId = Integer.parseInt(request.getParameter("userId"));
        String currentRole = currentUser.getRole().toLowerCase();

        // Prevent acting on self (delete / demote)
        boolean isSelf = (targetUserId == currentUser.getUserId());

        // Look up the target user's role for permission checks
        // (We reuse the list approach — simple for this scale)
        User targetUser = null;
        List<User> allUsers = userDAO.getAllUsers();
        for (User u : allUsers) {
            if (u.getUserId() == targetUserId) {
                targetUser = u;
                break;
            }
        }

        if (targetUser == null) {
            session.setAttribute("failMessage", "User not found.");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        String targetRole = targetUser.getRole() != null ? targetUser.getRole().toLowerCase() : "";

        switch (action) {

            case "approve":
                if (userDAO.approveUser(targetUserId)) {
                    session.setAttribute("successMessage", "User approved successfully.");
                } else {
                    session.setAttribute("failMessage", "Failed to approve user.");
                }
                break;

            case "delete":
                if (isSelf) {
                    session.setAttribute("failMessage", "You cannot delete your own account.");
                } else if ("co-admin".equals(currentRole) && !"student".equals(targetRole)) {
                    // Co-admin can only delete students
                    session.setAttribute("failMessage", "Co-Admins can only delete students.");
                } else if (userDAO.deleteUser(targetUserId)) {
                    session.setAttribute("successMessage", "User deleted successfully.");
                } else {
                    session.setAttribute("failMessage", "Failed to delete user.");
                }
                break;

            case "promote-coadmin":
                if (!"student".equals(targetRole)) {
                    session.setAttribute("failMessage", "Only students can be promoted to Co-Admin.");
                } else if (userDAO.updateUserRole(targetUserId, "co-admin")) {
                    session.setAttribute("successMessage", "User promoted to Co-Admin.");
                } else {
                    session.setAttribute("failMessage", "Failed to update user role.");
                }
                break;

            case "demote-student":
                if (!"admin".equals(currentRole)) {
                    session.setAttribute("failMessage", "Only Admins can demote Co-Admins.");
                } else if (isSelf) {
                    session.setAttribute("failMessage", "You cannot demote yourself.");
                } else if (userDAO.updateUserRole(targetUserId, "student")) {
                    session.setAttribute("successMessage", "User demoted to Student.");
                } else {
                    session.setAttribute("failMessage", "Failed to update user role.");
                }
                break;

            default:
                session.setAttribute("failMessage", "Unknown action.");
                break;
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}