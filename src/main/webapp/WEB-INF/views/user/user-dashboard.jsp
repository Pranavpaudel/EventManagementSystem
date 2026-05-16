<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="welcome-banner">
  <h2>Welcome back, ${sessionScope.user.fullName}!</h2>
  <p>You are logged in as a student. Use the links below to explore events.</p>
</div>

<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert--success">${sessionScope.successMessage}</div>
  <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.failMessage}">
  <div class="alert alert--danger">${sessionScope.failMessage}</div>
  <c:remove var="failMessage" scope="session"/>
</c:if>

<!-- Stats -->
<div class="stats-grid">
  <div class="stat-card">
    <div class="stat-card__value">${confirmedCount}</div>
    <div class="stat-card__label">Events Registered</div>
  </div>
  <div class="stat-card">
    <div class="stat-card__value">${upcomingEvents.size()}</div>
    <div class="stat-card__label">Upcoming Events</div>
  </div>
  <div class="stat-card">
    <div class="stat-card__value">${cancelledCount}</div>
    <div class="stat-card__label">Cancelled Bookings</div>
  </div>
</div>

<div class="quick-actions">
  <a href="${ctx}/events" class="btn btn--accent">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <rect x="3" y="4" width="18" height="18" rx="2"/>
      <line x1="16" y1="2" x2="16" y2="6"/>
      <line x1="8"  y1="2" x2="8"  y2="6"/>
      <line x1="3"  y1="10" x2="21" y2="10"/>
    </svg>
    View Events
  </a>
  <a href="${ctx}/my-bookings" class="btn btn--outline">My Bookings</a>
  <a href="${ctx}/wishlist"    class="btn btn--outline">My Wishlist</a>
</div>

<!-- Upcoming Events -->
<h2 class="page-subtitle">Upcoming Events</h2>
<table class="data-table">
  <thead>
    <tr>
      <th>Event Name</th>
      <th>Date</th>
      <th>Location</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty upcomingEvents}">
        <c:forEach var="event" items="${upcomingEvents}" begin="0" end="1">
    <tr>
      <td>${event.eventName}</td>
      <td>${event.eventDate}</td>
      <td>${event.location}</td>
      <td>
        <c:choose>
          <c:when test="${bookedEventIds.contains(event.eventId)}">
            <span class="status-badge status-badge--approved">Registered</span>
          </c:when>
          <c:when test="${bookingCounts[event.eventId] >= event.capacity}">
            <span class="status-badge status-badge--pending">Fully Booked</span>
          </c:when>
          <c:otherwise>
            <form action="${ctx}/book-event" method="post" style="display:inline;">
              <input type="hidden" name="eventId" value="${event.eventId}">
              <button type="submit" class="btn btn--primary btn--sm">Register</button>
            </form>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row"><td colspan="4">No upcoming events.</td></tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>
<br>
<a href="${ctx}/events" class="btn btn--outline btn--sm">View All Events &rarr;</a>

<!-- Recent Bookings -->
<h2 class="page-subtitle">My Recent Bookings</h2>
<table class="data-table">
  <thead>
    <tr>
      <th>Event Name</th>
      <th>Booking Date</th>
      <th>Status</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty myBookings}">
        <c:forEach var="booking" items="${myBookings}" end="4">
    <tr>
      <td>Event #${booking.eventId}</td>
      <td>${booking.bookingDate}</td>
      <td>${booking.status}</td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row"><td colspan="3">No bookings yet.</td></tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>
<br>
<a href="${ctx}/my-bookings" class="btn btn--outline btn--sm">View All Bookings &rarr;</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
