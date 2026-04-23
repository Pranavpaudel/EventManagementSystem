<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/19/2026
  Time: 2:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>

<html>
<head>
  <title>Admin User Approval</title>
</head>
<body>

<h2>Pending User Approvals</h2>

<table border="1" cellpadding="5">
  <tr>
    <th>Full Name</th>
    <th>Email</th>
    <th>Contact</th>
    <th>Action</th>
  </tr>

  <%
    List<User> users = (List<User>) request.getAttribute("pendingUsers");

    if (users != null && !users.isEmpty()) {
      for (User user : users) {
  %>
  <tr>
    <td><%= user.getFullName() %></td>
    <td><%= user.getEmail() %></td>
    <td><%= user.getContact() %></td>
    <td>
      <form action="<%= request.getContextPath() %>/admin/users" method="post">
        <input type="hidden" name="userId" value="<%= user.getUserId() %>">
        <input type="submit" value="Approve">
      </form>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="4">No pending users found.</td>
  </tr>
  <%
    }
  %>

</table>

<br>
<a href="<%= request.getContextPath() %>/admin-dashboard.jsp">
  Back to Admin Dashboard
</a>

</body>
</html>