<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== Admin Dashboard Content ===== -->
<div class="welcome-banner">
  <h2>Admin Dashboard</h2>
  <p>Manage events, approve users, and keep everything running smoothly.</p>
</div>

<c:if test="${not empty sessionScope.successMessage}">
<div class="alert alert--success">${sessionScope.successMessage}</div>
<c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.failMessage}">
<div class="alert alert--danger">${sessionScope.failMessage}</div>
<c:remove var="failMessage" scope="session"/>
</c:if>

<div class="stats-grid">
  <div class="stat-card">
    <div class="stat-card__value">${stats.totalEvents}</div>
    <div class="stat-card__label">Total Events</div>
  </div>
  <div class="stat-card">
    <div class="stat-card__value">${stats.totalUsers}</div>
    <div class="stat-card__label">Total Users</div>
  </div>
  <div class="stat-card">
    <div class="stat-card__value">${stats.totalBookings}</div>
    <div class="stat-card__label">Total Bookings</div>
  </div>
  <div class="stat-card">
    <div class="stat-card__value">${stats.pendingUsers}</div>
    <div class="stat-card__label">Pending Approvals</div>
  </div>
</div>

<div class="quick-actions">
  <a href="${pageContext.request.contextPath}/admin/add-event" class="btn btn--primary">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <circle cx="12" cy="12" r="10"/>
      <line x1="12" y1="8" x2="12" y2="16"/>
      <line x1="8" y1="12" x2="16" y2="12"/>
    </svg>
    Add New Event
  </a>
  <a href="${pageContext.request.contextPath}/admin/users" class="btn btn--accent">
    Manage Users
  </a>
</div>

<h2 class="page-subtitle">Manage Events</h2>

<table class="data-table">
  <thead>
    <tr>
      <th>Image</th>
      <th>Name</th>
      <th>Date</th>
      <th>Time</th>
      <th>Location</th>
      <th>Participants</th>
      <th>Capacity</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty events}">
        <c:forEach var="event" items="${events}">
    <tr>
      <td>
        <c:choose>
          <c:when test="${event.image.startsWith('static/')}">
            <img src="${pageContext.request.contextPath}/${event.image}" alt="" width="80" style="border-radius:4px;object-fit:cover;height:55px;">
          </c:when>
          <c:otherwise>
            <img src="${pageContext.request.contextPath}/event-uploads/${event.image}" alt="" width="80" style="border-radius:4px;object-fit:cover;height:55px;">
          </c:otherwise>
        </c:choose>
      </td>
      <td>${event.eventName}</td>
      <td>${event.eventDate}</td>
      <td>${event.eventTime}</td>
      <td>${event.location}</td>
      <td>${event.categoryId}</td>
      <td>${event.capacity}</td>
      <td>
        <a class="action-link action-link--edit"
           href="${pageContext.request.contextPath}/admin/edit-event?eventId=${event.eventId}">
          Edit
        </a>
        <a class="action-link action-link--delete"
           href="${pageContext.request.contextPath}/admin/delete-event?eventId=${event.eventId}"
           onclick="return confirm('Are you sure you want to delete this event?');">
          Delete
        </a>
      </td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row">
      <td colspan="8">No events available.</td>
    </tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
