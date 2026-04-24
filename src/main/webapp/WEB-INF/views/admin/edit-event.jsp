<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/24/2026
  Time: 2:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.college.eventms.entity.Event" %>

<%
  Event event = (Event) request.getAttribute("event");
%>

<html>
<head>
  <title>Edit Event</title>
</head>
<body>

<h2>Edit Event</h2>

<!-- FORM START -->
<form action="<%= request.getContextPath() %>/admin/edit-event" method="post">

  <input type="hidden" name="eventId" value="<%= event.getEventId() %>">

  <label>Event Name:</label><br>
  <input type="text" name="eventName"
         value="<%= event.getEventName() %>" required><br><br>

  <label>Description:</label><br>
  <textarea name="description" required><%= event.getDescription() %></textarea><br><br>

  <label>Event Date:</label><br>
  <input type="date" name="eventDate"
         value="<%= event.getEventDate() %>" required><br><br>

  <label>Event Time:</label><br>
  <input type="time" name="eventTime"
         value="<%= event.getEventTime() %>" required><br><br>

  <label>Location:</label><br>
  <input type="text" name="location"
         value="<%= event.getLocation() %>" required><br><br>

  <input type="submit" value="Update Event">
</form>
<!-- FORM END -->

<br>

<a href="<%= request.getContextPath() %>/admin/dashboard">
  Back to Dashboard
</a>

</body>
</html>