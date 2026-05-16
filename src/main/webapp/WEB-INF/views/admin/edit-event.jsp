<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="event" value="${requestScope.event}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<!-- ===== Edit Event Content ===== -->
<h1 class="page-title">Edit Event</h1>

<c:if test="${not empty error}">
  <div class="alert alert--danger">${error}</div>
</c:if>

<div class="card card--centered">
  <form action="${ctx}/admin/edit-event" method="post" enctype="multipart/form-data">

    <input type="hidden" name="eventId"    value="${event.eventId}">
    <input type="hidden" name="createdBy"  value="${event.createdBy}">

    <div class="form-group">
      <label for="eventName">Event Name</label>
      <input id="eventName" class="form-control" type="text" name="eventName"
             value="${event.eventName}" required>
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea id="description" class="form-control" name="description"
                required>${event.description}</textarea>
    </div>

    <div class="form-group">
      <label for="eventDate">Event Date</label>
      <input id="eventDate" class="form-control" type="date" name="eventDate"
             value="${event.eventDate}" required>
    </div>

    <div class="form-group">
      <label for="eventTime">Event Time</label>
      <input id="eventTime" class="form-control" type="time" name="eventTime"
             value="${event.eventTime}" required>
    </div>

    <div class="form-group">
      <label for="capacity">Capacity</label>
      <input id="capacity" class="form-control" type="number" name="capacity"
             value="${event.capacity}" min="1" required>
    </div>

    <div class="form-group">
      <label for="categoryId">Category</label>
      <select id="categoryId" class="form-control" name="categoryId" required>
        <option value="">-- Select Category --</option>
        <c:forEach var="cat" items="${categories}">
          <option value="${cat.categoryId}"
                  ${cat.categoryId == event.categoryId ? 'selected' : ''}>
            ${cat.name}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="location">Location</label>
      <input id="location" class="form-control" type="text" name="location"
             value="${event.location}" required>
    </div>

    <div class="form-group">
      <label for="status">Status</label>
      <select id="status" class="form-control" name="status" required>
        <option value="UPCOMING"   ${event.status == 'UPCOMING'   ? 'selected' : ''}>Upcoming</option>
        <option value="ONGOING"    ${event.status == 'ONGOING'    ? 'selected' : ''}>Ongoing</option>
        <option value="COMPLETED"  ${event.status == 'COMPLETED'  ? 'selected' : ''}>Completed</option>
        <option value="CANCELLED"  ${event.status == 'CANCELLED'  ? 'selected' : ''}>Cancelled</option>
      </select>
    </div>

    <div class="form-group">
      <label>Event Image</label>
      <c:if test="${not empty event.image}">
        <div style="margin-bottom:.5rem;">
          <c:choose>
            <c:when test="${event.image.startsWith('static/')}">
              <img src="${ctx}/${event.image}" alt="Current image" width="80" style="border-radius:4px;">
            </c:when>
            <c:otherwise>
              <img src="${ctx}/event-uploads/${event.image}" alt="Current image" width="80" style="border-radius:4px;">
            </c:otherwise>
          </c:choose>
        </div>
        <p style="font-size:.85rem;color:#6B7280;margin:0 0 .4rem;">Upload a new image to replace, or leave empty to keep current.</p>
      </c:if>
      <input class="form-control" type="file" name="image" accept=".jpg,.jpeg,.png">
    </div>

    <button type="submit" class="btn btn--primary" style="width:100%;">Update Event</button>
  </form>
</div>

<br>
<a href="${ctx}/admin/dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
