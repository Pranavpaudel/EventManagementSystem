<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.Event" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<%
  List<Event> events = (List<Event>) request.getAttribute("events");
%>

<!-- ===== Admin Dashboard Content ===== -->
<div class="welcome-banner">
  <h2>Admin Dashboard</h2>
  <p>Manage events, approve users, and keep everything running smoothly.</p>
</div>

<%-- Success / Fail flash messages --%>
<%
  String msg = (String) session.getAttribute("successMessage");
  if (msg != null) {
%>
<div class="alert alert--success"><%= msg %></div>
<%
    session.removeAttribute("successMessage");
  }
%>

<%
  String fail = (String) session.getAttribute("failMessage");
  if (fail != null) {
%>
<div class="alert alert--danger"><%= fail %></div>
<%
    session.removeAttribute("failMessage");
  }
%>

<div class="quick-actions">
  <a href="<%= request.getContextPath() %>/admin/add-event" class="btn btn--primary">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <circle cx="12" cy="12" r="10"/>
      <line x1="12" y1="8" x2="12" y2="16"/>
      <line x1="8" y1="12" x2="16" y2="12"/>
    </svg>
    Add New Event
  </a>
  <a href="<%= request.getContextPath() %>/admin/users" class="btn btn--accent">
    Manage Users
  </a>
</div>

<h2 class="page-subtitle">Manage Events</h2>

<table class="data-table">
  <thead>
    <tr>
      <th>Name</th>
      <th>Date</th>
      <th>Time</th>
      <th>Location</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    <%
      if (events != null && !events.isEmpty()) {
        for (Event event : events) {
    %>
    <tr>
      <td><%= event.getEventName() %></td>
      <td><%= event.getEventDate() %></td>
      <td><%= event.getEventTime() %></td>
      <td><%= event.getLocation() %></td>
      <td>
        <a class="action-link action-link--edit"
           href="<%= request.getContextPath() %>/admin/edit-event?eventId=<%= event.getEventId() %>">
          Edit
        </a>
        <a class="action-link action-link--delete"
           href="<%= request.getContextPath() %>/admin/delete-event?eventId=<%= event.getEventId() %>"
           onclick="return confirm('Are you sure you want to delete this event?');">
          Delete
        </a>
      </td>
    </tr>
    <%
        }
      } else {
    %>
    <tr class="empty-row">
      <td colspan="5">No events available.</td>
    </tr>
    <%
      }
    %>
  </tbody>
</table>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
