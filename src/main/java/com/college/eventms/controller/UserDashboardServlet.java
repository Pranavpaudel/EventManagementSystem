package com.college.eventms.controller;

import com.college.eventms.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user-dashboard")
public class UserDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !user.getRole().equalsIgnoreCase("student")) {
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/user/user-dashboard.jsp")
                .forward(request, response);
    }
}