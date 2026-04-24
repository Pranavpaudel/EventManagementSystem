<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/19/2026
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Access Denied</title>
</head>
<body>

<h1>Access Denied</h1>
<p>You are not authorized to access this page.</p>

<a href="<%= request.getContextPath() %>/login">Go to Login</a>

</body>
</html>