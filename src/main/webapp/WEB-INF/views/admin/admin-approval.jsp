<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/23/2026
  Time: 11:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.User" %>

<html>
<head>
    <title>Admin - User Approval</title>
</head>
<body>

<h2>Pending User Approvals</h2>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Contact</th>
        <th>Email</th>
        <th>Action</th>
    </tr>

    <%
        List<User> users = (List<User>) request.getAttribute("pendingUsers");
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
    %>
    <tr>
        <td><%= user.getFullName() %></td>
        <td><%= user.getContact() %></td>
        <td><%= user.getEmail() %></td>
        <td>
            <form method="post"
                  action="<%=request.getContextPath()%>/admin/approve-users">
                <input type="hidden" name="userId"
                       value="<%= user.getUserId() %>">
                <input type="submit" value="Approve">
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">No pending users</td>
    </tr>
    <%
        }
    %>
</table>

<br>
<a href="<%=request.getContextPath()%>/admin-dashboard.jsp">Back</a>

</body>
</html>