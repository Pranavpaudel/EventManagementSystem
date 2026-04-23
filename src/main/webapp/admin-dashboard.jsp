<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/19/2026
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Admin Dashboard</title>
</head>
<body>

<h2>Admin Dashboard</h2>

<p>Welcome Admin!</p>


<a href=" <%=request.getContextPath()%>/add-event.jsp">Add New Event</a>
<br><br>


<%-- Success and Failure Messages--%>
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



<a href="<%= request.getContextPath() %>/logout">Logout</a>

</body>
</html>
