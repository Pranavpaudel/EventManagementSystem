package com.college.eventms.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Invalidates the current session, clears the remember-me cookie, and redirects to login.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie forget = new Cookie(LoginServlet.REMEMBER_COOKIE, "");
        forget.setMaxAge(0);
        forget.setHttpOnly(true);
        forget.setPath("/");
        response.addCookie(forget);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}