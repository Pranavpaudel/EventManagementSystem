<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.college.eventms.entity.Event" %>

<%
  Event event = (Event) request.getAttribute("event");
%>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<!-- ===== Edit Event Content ===== -->
<h1 class="page-title">Edit Event</h1>

<div class="card card--centered">
  <form action="<%= request.getContextPath() %>/admin/edit-event" method="post">

    <input type="hidden" name="eventId" value="<%= event.getEventId() %>">

    <div class="form-group">
      <label for="eventName">Event Name</label>
      <input id="eventName" class="form-control" type="text" name="eventName"
             value="<%= event.getEventName() %>" required>
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea id="description" class="form-control" name="description"
                required><%= event.getDescription() %></textarea>
    </div>

    <div class="form-group">
      <label for="eventDate">Event Date</label>
      <input id="eventDate" class="form-control" type="date" name="eventDate"
             value="<%= event.getEventDate() %>" required>
    </div>

    <div class="form-group">
      <label for="eventTime">Event Time</label>
      <input id="eventTime" class="form-control" type="time" name="eventTime"
             value="<%= event.getEventTime() %>" required>
    </div>

    <div class="form-group">
      <label for="location">Location</label>
      <input id="location" class="form-control" type="text" name="location"
             value="<%= event.getLocation() %>" required>
    </div>

    <button type="submit" class="btn btn--primary" style="width:100%;">Update Event</button>
  </form>
</div>

<br>
<a href="<%= request.getContextPath() %>/admin/dashboard" class="btn btn--outline">
  &larr; Back to Dashboard
</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />