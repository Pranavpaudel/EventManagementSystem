<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/23/2026
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Add Event</title>
</head>
<body>

<h2>Add New Event</h2>

<form action="<%=request.getContextPath()%>/admin/add-event" method="post">

  <label>Event Name:</label><br>
  <input type="text" name="eventName" required><br><br>

  <label>Description:</label><br>
  <textarea name="description" required></textarea><br><br>

  <label>Event Date:</label><br>
  <input type="date" name="eventDate" required><br><br>

  <label>Event Time:</label><br>
  <input type="time" name="eventTime" required><br><br>

  <label>Location:</label><br>
  <input type="text" name="location" required><br><br>

  <input type="submit" value="Add Event">
</form>

<br>
<a href="<%=request.getContextPath()%>/admin-dashboard.jsp">Back to Dashboard</a>

</body>
</html>
