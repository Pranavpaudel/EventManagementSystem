<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
  <c:when test="${empty sessionScope.user}">
    <c:set var="homeUrl" value="${pageContext.request.contextPath}/login" />
  </c:when>
  <c:when test="${sessionScope.user.role.toLowerCase() eq 'admin' or sessionScope.user.role.toLowerCase() eq 'co-admin'}">
    <c:set var="homeUrl" value="${pageContext.request.contextPath}/admin/dashboard" />
  </c:when>
  <c:otherwise>
    <c:set var="homeUrl" value="${pageContext.request.contextPath}/user-dashboard" />
  </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Event Management System — Manage and browse college events">
  <title>Event Management System</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>

<!-- ===== HEADER ===== -->
<header class="site-header">
  <a href="${homeUrl}" class="site-header__logo">
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
