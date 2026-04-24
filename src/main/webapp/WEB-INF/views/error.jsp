<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/19/2026
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Access Denied</title>
</head>
<body>

<h2>Access Denied</h2>
<p>You are not authorized to access this page.</p>

<a href="<%= request.getContextPath() %>/loginRegister/login.jsp">
  Go to Login
</a>

</body>
</html>