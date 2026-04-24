<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.Event" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<!-- ===== View Events Content ===== -->
<h1 class="page-title">Available Events</h1>

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
    <%
      List<Event> events = (List<Event>) request.getAttribute("events");
      if (events != null && !events.isEmpty()) {
        for (Event event : events) {
    %>
    <tr>
      <td><%= event.getEventName() %></td>
      <td><%= event.getDescription() %></td>
      <td><%= event.getEventDate() %></td>
      <td><%= event.getEventTime() %></td>
      <td><%= event.getLocation() %></td>
    </tr>
    <%
        }
      } else {
    %>
    <tr class="empty-row">
      <td colspan="5">No events available at this time.</td>
    </tr>
    <%
      }
    %>
  </tbody>
</table>

<br>
<a href="<%= request.getContextPath() %>/user-dashboard" class="btn btn--outline">
  &larr; Back to Dashboard
</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
