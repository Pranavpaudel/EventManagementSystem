<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.college.eventms.entity.User" %>

<%
    User headerUser = (User) session.getAttribute("user");
    String homeUrl;

    if (headerUser == null) {
        homeUrl = request.getContextPath() + "/login";
    } else if ("admin".equalsIgnoreCase(headerUser.getRole())
            || "co-admin".equalsIgnoreCase(headerUser.getRole())) {
        homeUrl = request.getContextPath() + "/admin/dashboard";
    } else {
        homeUrl = request.getContextPath() + "/user-dashboard";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Event Management System — Manage and browse college events">
  <title>Event Management System</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/style.css">
</head>
<body>

<!-- ===== HEADER ===== -->
<header class="site-header">
  <a href="<%= homeUrl %>" class="site-header__logo">
    <!-- Calendar / Event icon -->
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <rect x="3" y="4" width="18" height="18" rx="2"/>
      <line x1="16" y1="2" x2="16" y2="6"/>
      <line x1="8"  y1="2" x2="8"  y2="6"/>
      <line x1="3"  y1="10" x2="21" y2="10"/>
      <path d="M8 14h.01M12 14h.01M16 14h.01M8 18h.01M12 18h.01M16 18h.01"/>
    </svg>
    Event Management System
  </a>
  <span class="site-header__tagline">College Event Portal</span>
</header>
