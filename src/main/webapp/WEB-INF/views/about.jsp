<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== About Content ===== -->
<h1 class="page-title">About Us</h1>

<div class="card">
  <h2 class="page-subtitle">Event Management System</h2>
  <p>
    The Event Management System was developed for Informatics College as part of the
    CS5054NP module. It provides a centralised platform for organising and participating
    in college events.
  </p>

  <h2 class="page-subtitle">What It Does</h2>
  <p>
    Students can browse upcoming events, register their attendance, view their booking
    history, and cancel bookings at any time. Administrators and co-admins have full
    control over the event lifecycle — creating, editing, and removing events — as well
    as managing user accounts and approving new registrations.
  </p>

  <h2 class="page-subtitle">Tech Stack</h2>
  <p>
    The system is built with <strong>Java Servlets</strong> and <strong>JSP</strong>
    following the <strong>MVC pattern</strong>, backed by a <strong>MySQL</strong>
    database and deployed on <strong>Apache Tomcat</strong>. The frontend uses
    pure CSS with no external frameworks.
  </p>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
