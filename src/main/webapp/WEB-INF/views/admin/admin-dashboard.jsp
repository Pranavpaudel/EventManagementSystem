<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/19/2026
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Admin Dashboard</title>
</head>
<body>

<h2>Admin Dashboard</h2>

<p>Welcome Admin!</p>

<!-- Link to SERVLET, not JSP -->
<a href="<%= request.getContextPath() %>/admin/add-event">Add New Event</a>
<br><br>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.Event" %>

<%
  List<Event> events = (List<Event>) request.getAttribute("events");
%>

<h3>Manage Events</h3>

<table border="1" cellpadding="5">
  <tr>
    <th>Name</th>
    <th>Date</th>
    <th>Time</th>
    <th>Location</th>
    <th>Actions</th>
  </tr>

  <%
    if (events != null && !events.isEmpty()) {
      for (Event event : events) {
  %>
  <tr>
    <td><%= event.getEventName() %></td>
    <td><%= event.getEventDate() %></td>
    <td><%= event.getEventTime() %></td>
    <td><%= event.getLocation() %></td>
    <td>
      <a href="<%= request.getContextPath() %>/admin/edit-event?eventId=<%= event.getEventId() %>">
        Edit
      </a>
      <a href="<%= request.getContextPath() %>/admin/delete-event?eventId=<%= event.getEventId() %>" onclick="return confirm('Are you sure you want to delete this event?');">
      Delete
    </a></td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="5">No events available.</td>
  </tr>
  <%
    }
  %>
</table>


<%
  String msg = (String) session.getAttribute("successMessage");
  if (msg != null) {
%>
<p style="color: green;"><%= msg %></p>
<%
    session.removeAttribute("successMessage");
  }
%>

<%
  String fail = (String) session.getAttribute("failMessage");
  if (fail != null) {
%>
<p style="color: red;"><%= fail %></p>
<%
    session.removeAttribute("failMessage");
  }
%>

<br>

<a href="<%= request.getContextPath() %>/logout">Logout</a>

</body>
</html>
