<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/19/2026
  Time: 12:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>

<h2>User Login</h2>

<form action="<%= request.getContextPath() %>/login" method="post">

  <label>Contact:</label><br>
  <input type="text" name="contact" required><br><br>

  <label>Password:</label><br>
  <input type="password" name="password" required><br><br>

  <input type="submit" value="Login">

</form>

<%
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
  }
%>

</body>
</html>