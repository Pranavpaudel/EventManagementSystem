<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== My Bookings Content ===== -->
<h1 class="page-title">My Bookings</h1>

<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert--success">${sessionScope.successMessage}</div>
  <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.failMessage}">
  <div class="alert alert--danger">${sessionScope.failMessage}</div>
  <c:remove var="failMessage" scope="session"/>
</c:if>

<table class="data-table">
  <thead>
    <tr>
      <th>Event Name</th>
      <th>Date</th>
      <th>Time</th>
      <th>Location</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty bookings}">
        <c:forEach var="booking" items="${bookings}">
    <tr>
      <td>${booking.eventName}</td>
      <td>${booking.eventDate}</td>
      <td>${booking.eventTime}</td>
      <td>${booking.location}</td>
      <td>${booking.status}</td>
      <td>
        <c:if test="${booking.status != 'CANCELLED'}">
          <form id="cancelForm${booking.bookingId}" action="${ctx}/cancel-booking" method="post" style="display:inline;">
            <input type="hidden" name="bookingId" value="${booking.bookingId}">
            <button type="button" class="btn btn--danger btn--sm"
                    onclick="showConfirm('Cancel Booking','Are you sure you want to cancel this booking?',function(){ document.getElementById('cancelForm${booking.bookingId}').submit(); })">Cancel</button>
          </form>
        </c:if>
        <c:if test="${booking.status == 'CANCELLED'}">
          <span class="text-muted">—</span>
        </c:if>
      </td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row">
      <td colspan="6">You have no bookings yet.</td>
    </tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<br>
<a href="${ctx}/user-dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
