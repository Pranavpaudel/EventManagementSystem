<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.User" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<%
  User currentUser = (User) session.getAttribute("user");
  int currentUserId = (currentUser != null) ? currentUser.getUserId() : -1;
  String myRole = (currentUser != null && currentUser.getRole() != null) ? currentUser.getRole().toLowerCase() : "";
  boolean isAdmin = "admin".equals(myRole);
  boolean isCoadmin = "co-admin".equals(myRole);
  List<User> allUsers = (List<User>) request.getAttribute("allUsers");
  String ctx = request.getContextPath();
%>

<h1 class="page-title">Manage Users</h1>

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

<table class="data-table">
  <thead>
    <tr>
      <th>Full Name</th>
      <th>Email</th>
      <th>Contact</th>
      <th>Role</th>
      <th>Status</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    <%
      if (allUsers != null && !allUsers.isEmpty()) {
        for (User u : allUsers) {
          boolean isSelf = (u.getUserId() == currentUserId);
          String role   = u.getRole() != null ? u.getRole().toLowerCase() : "";
          String status = u.getStatus() != null ? u.getStatus().toLowerCase() : "";
    %>
    <tr>
      <td><%= u.getFullName() %></td>
      <td><%= u.getEmail() %></td>
      <td><%= u.getContact() %></td>
      <td><%= u.getRole() %></td>
      <td>
        <% if ("pending".equals(status)) { %>
          <span class="status-badge status-badge--pending">Pending</span>
        <% } else { %>
          <span class="status-badge status-badge--approved">Approved</span>
        <% } %>
      </td>
      <td>
        <%-- Approve button (pending users — both admin and co-admin can approve) --%>
        <% if ("pending".equals(status)) { %>
        <form action="<%= ctx %>/admin/users" method="post" style="display:inline;">
          <input type="hidden" name="action" value="approve">
          <input type="hidden" name="userId" value="<%= u.getUserId() %>">
          <button type="submit" class="btn btn--success btn--sm"
                  onclick="return confirm('Approve this user?');">Approve</button>
        </form>
        <% } %>

        <%-- Promote student → co-admin (both admin and co-admin can do this) --%>
        <% if ("student".equals(role) && "approved".equals(status)) { %>
        <form action="<%= ctx %>/admin/users" method="post" style="display:inline;">
          <input type="hidden" name="action" value="promote-coadmin">
          <input type="hidden" name="userId" value="<%= u.getUserId() %>">
          <button type="submit" class="btn btn--accent btn--sm"
                  onclick="return confirm('Promote this user to Co-Admin?');">Promote Co-Admin</button>
        </form>
        <% } %>

        <%-- Demote co-admin → student (admin only, not self) --%>
        <% if (isAdmin && "co-admin".equals(role) && !isSelf) { %>
        <form action="<%= ctx %>/admin/users" method="post" style="display:inline;">
          <input type="hidden" name="action" value="demote-student">
          <input type="hidden" name="userId" value="<%= u.getUserId() %>">
          <button type="submit" class="btn btn--outline btn--sm"
                  onclick="return confirm('Demote this Co-Admin to Student?');">Demote</button>
        </form>
        <% } %>

        <%-- Delete button --%>
        <%-- Admin: can delete anyone except self --%>
        <%-- Co-Admin: can delete students only --%>
        <% if (!isSelf && (isAdmin || (isCoadmin && "student".equals(role)))) { %>
        <form action="<%= ctx %>/admin/users" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="userId" value="<%= u.getUserId() %>">
          <button type="submit" class="btn btn--danger btn--sm"
                  onclick="return confirm('Are you sure you want to delete this user?');">Delete</button>
        </form>
        <% } %>

        <%-- Self indicator --%>
        <% if (isSelf) { %>
          <span class="status-badge status-badge--self">You</span>
        <% } %>
      </td>
    </tr>
    <%
        }
      } else {
    %>
    <tr class="empty-row">
      <td colspan="6">No users found.</td>
    </tr>
    <%
      }
    %>
  </tbody>
</table>

<br>
<a href="<%= ctx %>/admin/dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
