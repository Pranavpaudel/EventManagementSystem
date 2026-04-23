package controller;

import dao.EventDAO;
import model.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/events")
public class ViewEventServlet extends HttpServlet {

    private EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Event> events = eventDAO.getAllEvents();
        request.setAttribute("events", events);

        request.getRequestDispatcher("/WEB-INF/views/view-events.jsp")
                .forward(request, response);
    }
}