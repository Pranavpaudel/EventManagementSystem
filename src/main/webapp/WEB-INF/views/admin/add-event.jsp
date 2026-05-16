<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== Add Event Content ===== -->
<h1 class="page-title">Add New Event</h1>

<c:if test="${not empty error}">
  <div class="alert alert--danger">${error}</div>
</c:if>

<div class="card card--centered">
  <form id="addEventForm" action="${ctx}/admin/add-event" method="post" enctype="multipart/form-data">

    <div class="form-group">
      <label for="eventName">Event Name</label>
      <input id="eventName" class="form-control" type="text" name="eventName"
             placeholder="Enter event name" required>
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea id="description" class="form-control" name="description"
                placeholder="Describe the event" required></textarea>
    </div>

    <div class="form-group">
      <label for="eventDate">Event Date</label>
      <input id="eventDate" class="form-control" type="date" name="eventDate" required>
    </div>

    <div class="form-group">
      <label for="eventTime">Event Time</label>
      <input id="eventTime" class="form-control" type="time" name="eventTime" required>
    </div>

    <div class="form-group">
      <label for="capacity">Capacity</label>
      <input id="capacity" class="form-control" type="number" name="capacity"
             placeholder="Maximum number of attendees" min="1" required>
    </div>

    <div class="form-group">
      <label for="categoryId">Category</label>
      <select id="categoryId" class="form-control" name="categoryId" required>
        <option value="">-- Select Category --</option>
        <c:forEach var="cat" items="${categories}">
          <option value="${cat.categoryId}">${cat.name}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="location">Location</label>
      <input id="location" class="form-control" type="text" name="location"
             placeholder="Enter venue / location" required>
    </div>

    <div class="form-group">
      <label for="image">Event Image</label>
      <input id="image" class="form-control" type="file" name="image" accept=".jpg,.jpeg,.png">
    </div>

    <button type="submit" class="btn btn--primary" style="width:100%;">Add Event</button>
  </form>
</div>

<br>
<a href="${ctx}/admin/dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
