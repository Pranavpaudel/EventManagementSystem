<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.college.eventms.entity.User" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<%
  User dashUser = (User) session.getAttribute("user");
%>

<!-- ===== User Dashboard Content ===== -->
<div class="welcome-banner">
  <h2>Welcome back, <%= dashUser != null ? dashUser.getFullName() : "User" %>!</h2>
  <p>You are logged in as a student. Use the links below to explore events.</p>
</div>

<div class="quick-actions">
  <a href="<%= request.getContextPath() %>/events" class="btn btn--accent">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <rect x="3" y="4" width="18" height="18" rx="2"/>
      <line x1="16" y1="2" x2="16" y2="6"/>
      <line x1="8"  y1="2" x2="8"  y2="6"/>
      <line x1="3"  y1="10" x2="21" y2="10"/>
    </svg>
    View Events
  </a>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />