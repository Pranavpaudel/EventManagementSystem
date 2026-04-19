package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contact = request.getParameter("contact");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByContact(contact);

        if (user == null) {
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (!user.getPassword().equals(password)) {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (!user.getStatus().equalsIgnoreCase("approved")) {
            request.setAttribute("error", "Account not approved by admin");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Create session
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);

        //  Redirect based on role
        if (user.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/user-dashboard.jsp");
        }
    }
}