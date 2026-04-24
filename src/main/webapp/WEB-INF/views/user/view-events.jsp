<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/23/2026
  Time: 3:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.Event" %>

<html>
<head>
    <title>Available Events</title>
</head>
<body>

<h2>Available Events</h2>

<table border="1">
    <tr>
        <th>Event Name</th>
        <th>Description</th>
        <th>Date</th>
        <th>Time</th>
        <th>Location</th>
    </tr>

    <%
        List<Event> events = (List<Event>) request.getAttribute("events");
        if (events != null && !events.isEmpty()) {
            for (Event event : events) {
    %>
    <tr>
        <td><%= event.getEventName() %></td>
        <td><%= event.getDescription() %></td>
        <td><%= event.getEventDate() %></td>
        <td><%= event.getEventTime() %></td>
        <td><%= event.getLocation() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">No events available</td>
    </tr>
    <%
        }
    %>
</table>

<br>
<a href="<%=request.getContextPath()%>/user/dashboard">Back to Dashboard</a>

</body>
</html>
