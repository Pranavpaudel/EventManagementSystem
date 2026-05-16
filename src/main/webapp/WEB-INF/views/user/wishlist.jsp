<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<h1 class="page-title">My Wishlist</h1>

<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert--success">${sessionScope.successMessage}</div>
  <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.failMessage}">
  <div class="alert alert--danger">${sessionScope.failMessage}</div>
  <c:remove var="failMessage" scope="session"/>
</c:if>

<c:choose>
  <c:when test="${empty wishlistEvents}">
    <div class="card" style="text-align:center;padding:2rem;">
      <p>Your wishlist is empty.</p>
      <a href="${ctx}/events" class="btn btn--primary">Browse Events</a>
    </div>
  </c:when>
  <c:otherwise>
    <table class="data-table">
      <thead>
        <tr>
          <th>Event Name</th>
          <th>Date</th>
          <th>Time</th>
          <th>Location</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="event" items="${wishlistEvents}">
        <tr>
          <td>${event.eventName}</td>
          <td>${event.eventDate}</td>
          <td>${event.eventTime}</td>
          <td>${event.location}</td>
          <td style="display:flex;gap:.5rem;">
            <form action="${ctx}/book-event" method="post" style="display:inline;">
              <input type="hidden" name="eventId" value="${event.eventId}">
              <button type="submit" class="btn btn--primary btn--sm">Register</button>
            </form>
            <form action="${ctx}/wishlist/remove" method="post" style="display:inline;">
              <input type="hidden" name="eventId" value="${event.eventId}">
              <button type="submit" class="btn btn--danger btn--sm"
                      onclick="return confirm('Remove from wishlist?')">Remove</button>
            </form>
          </td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>

<br>
<a href="${ctx}/user-dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
