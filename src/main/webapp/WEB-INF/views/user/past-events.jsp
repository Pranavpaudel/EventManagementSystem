<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<h1 class="page-title">Past Events</h1>

<table class="data-table">
  <thead>
    <tr>
      <th>Event Name</th>
      <th>Description</th>
      <th>Date</th>
      <th>Time</th>
      <th>Location</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${not empty events}">
        <c:forEach var="event" items="${events}">
    <tr>
      <td>${event.eventName}</td>
      <td>${event.description}</td>
      <td>${event.eventDate}</td>
      <td>${event.eventTime}</td>
      <td>${event.location}</td>
    </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
    <tr class="empty-row"><td colspan="5">No past events found.</td></tr>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<br>
<a href="${ctx}/user-dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
