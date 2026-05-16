<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<h1 class="page-title">Available Events</h1>

<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert--success">${sessionScope.successMessage}</div>
  <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.failMessage}">
  <div class="alert alert--danger">${sessionScope.failMessage}</div>
  <c:remove var="failMessage" scope="session"/>
</c:if>

<div class="card">
  <form method="get" action="${ctx}/events">
    <div style="display:flex;gap:.75rem;flex-wrap:wrap;align-items:flex-end;">
      <div style="flex:2;min-width:180px;">
        <input class="form-control" type="text" name="keyword"
               placeholder="Search by name, description, or location"
               value="${keyword}">
      </div>
      <div style="flex:1;min-width:140px;">
        <select class="form-control" name="categoryId">
          <option value="">All Categories</option>
          <c:forEach var="cat" items="${categories}">
            <option value="${cat.categoryId}"
                    ${selectedCategoryId == cat.categoryId ? 'selected' : ''}>
              ${cat.name}
            </option>
          </c:forEach>
        </select>
      </div>
      <div style="flex:1;min-width:130px;">
        <input class="form-control" type="date" name="dateFrom"
               value="${selectedDateFrom}" title="From">
      </div>
      <div style="flex:1;min-width:130px;">
        <input class="form-control" type="date" name="dateTo"
               value="${selectedDateTo}" title="To">
      </div>
      <div style="display:flex;gap:.5rem;">
        <button type="submit" class="btn btn--primary">Search</button>
        <a href="${ctx}/events" class="btn btn--outline">Clear</a>
      </div>
    </div>
  </form>
</div>

<table class="data-table">
  <thead>
    <tr>
      <th>Image</th>
      <th>Event Name</th>
      <th>Description</th>
      <th>Date</th>
      <th>Time</th>
      <th>Location</th>
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
            <img src="${ctx}/${event.image}" alt="" width="80" style="border-radius:4px;object-fit:cover;height:55px;">
          </c:when>
          <c:otherwise>
            <img src="${ctx}/event-uploads/${event.image}" alt="" width="80" style="border-radius:4px;object-fit:cover;height:55px;">
          </c:otherwise>
        </c:choose>
      </td>
      <td>${event.eventName}</td>
      <td>${event.description}</td>
      <td>${event.eventDate}</td>
      <td>${event.eventTime}</td>
      <td>${event.location}</td>
      <td style="display:flex;gap:.5rem;align-items:center;">
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
        <form action="${ctx}/wishlist" method="post" style="display:inline;">
          <input type="hidden" name="eventId" value="${event.eventId}">
          <button type="submit" class="btn btn--outline btn--sm">&#9825; Save</button>
        </form>
      </td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row">
      <td colspan="7">No events found.</td>
    </tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<br>
<a href="${ctx}/user-dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />

