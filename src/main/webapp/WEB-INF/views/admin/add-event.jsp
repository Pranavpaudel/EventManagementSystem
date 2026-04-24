<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<!-- ===== Add Event Content ===== -->
<h1 class="page-title">Add New Event</h1>

<div class="card card--centered">
  <form action="<%= request.getContextPath() %>/admin/add-event" method="post">

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
      <input type="checkbox" name="isRecurring">Repeat event
    </div>

    <div class="form-group">
      <label for="location">Location</label>
      <input id="location" class="form-control" type="text" name="location"
             placeholder="Enter venue / location" required>
    </div>

    <button type="submit" class="btn btn--primary" style="width:100%;">Add Event</button>
  </form>
</div>

<br>
<a href="<%= request.getContextPath() %>/admin/dashboard" class="btn btn--outline">
  &larr; Back to Dashboard
</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
