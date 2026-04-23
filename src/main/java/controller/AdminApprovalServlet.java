package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/approve-users")
public class AdminApprovalServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    // Show all pending users
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> pendingUsers = userDAO.getPendingUsers();
        request.setAttribute("pendingUsers", pendingUsers);

        request.getRequestDispatcher("/jsp/admin-approval.jsp")
                .forward(request, response);
    }

    // Approve a specific user
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.approveUser(userId);

        // Refresh the page after approval
        response.sendRedirect(request.getContextPath() + "/admin/approve-users");
    }
}