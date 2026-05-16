<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<h1 class="page-title">Reports &amp; Analytics</h1>

<!-- Summary Stats -->
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

<!-- Event Attendance -->
<h2 class="page-subtitle">Event Attendance</h2>
<table class="data-table">
  <thead>
    <tr>
      <th>Event Name</th>
      <th>Date</th>
      <th>Capacity</th>
      <th>Registrations</th>
      <th>Fill</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty attendanceReport}">
        <c:forEach var="row" items="${attendanceReport}">
    <tr>
      <td>${row.eventName}</td>
      <td>${row.eventDate}</td>
      <td>${row.capacity}</td>
      <td>${row.totalBookings}</td>
      <td>${row.totalBookings} / ${row.capacity}</td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row"><td colspan="5">No data available.</td></tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<!-- Category Breakdown -->
<h2 class="page-subtitle">Category Breakdown</h2>
<table class="data-table">
  <thead>
    <tr>
      <th>Category</th>
      <th>Number of Events</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty categoryBreakdown}">
        <c:forEach var="row" items="${categoryBreakdown}">
    <tr>
      <td>${row.categoryName}</td>
      <td>${row.eventCount}</td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row"><td colspan="2">No data available.</td></tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<br>
<a href="${ctx}/admin/dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
