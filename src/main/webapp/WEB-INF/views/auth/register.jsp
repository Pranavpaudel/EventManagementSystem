<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>User Registration</title>
</head>
<body>

<h2>User Registration</h2>

<form action="<%= request.getContextPath() %>/register" method="post">

  <label>Full Name:</label><br>
  <input type="text" name="fullName" required><br><br>

  <label>Contact:</label><br>
  <input type="text" name="contact" required><br><br>

  <label>Email:</label><br>
  <input type="email" name="email" required><br><br>

  <label>Password:</label><br>
  <input type="password" name="password" required><br><br>

  <input type="submit" value="Register">
</form>

</body>
</html>