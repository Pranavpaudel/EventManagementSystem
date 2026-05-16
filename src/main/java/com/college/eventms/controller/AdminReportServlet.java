package com.college.eventms.controller;

import com.college.eventms.entity.User;
import com.college.eventms.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet that serves the admin reports page — attendance by event, category breakdown, and summary stats.
 */
@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null
                || (!user.getRole().equalsIgnoreCase("admin")
                && !user.getRole().equalsIgnoreCase("co-admin"))) {
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("attendanceReport",  reportService.getAttendanceReport());
        request.setAttribute("categoryBreakdown", reportService.getCategoryBreakdown());
        request.setAttribute("stats",             reportService.getSummaryStats());

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-reports.jsp")
                .forward(request, response);
    }
}
