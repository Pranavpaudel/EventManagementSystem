<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.college.eventms.entity.User" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<%
  User currentUser = (User) session.getAttribute("user");
  int currentUserId = currentUser != null ? currentUser.getUserId() : -1;
  String myRole = currentUser != null ? currentUser.getRole().toLowerCase() : "";
  boolean isAdmin = "admin".equals(myRole);
  boolean isCoadmin = "co-admin".equals(myRole);

  List<User> allUsers = (List<User>) request.getAttribute("allUsers");
  String ctx = request.getContextPath();
%>

<h1 class="page-title">Manage Users</h1>

<%
  String msg = (String) session.getAttribute("successMessage");
  if (msg != null) {
%>
<div class="alert alert--success"><%= msg %></div>
<%
    session.removeAttribute("successMessage");
  }

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
        boolean isSelf = u.getUserId() == currentUserId;
        String role = u.getRole().toLowerCase();
        String status = u.getStatus().toLowerCase();
        String formId;
  %>
  <tr>
    <td><%= u.getFullName() %></td>
    <td><%= u.getEmail() %></td>
    <td><%= u.getContact() %></td>
    <td><%= u.getRole() %></td>
    <td>
    <span class="status-badge status-badge--<%= status %>">
        <%= status %>
    </span>
    </td>

    <td>

      <!-- APPROVE -->
      <% if ("pending".equals(status)) {
        formId = "approveForm_" + u.getUserId();
      %>
      <form id="<%= formId %>" action="<%= ctx %>/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="approve">
        <input type="hidden" name="userId" value="<%= u.getUserId() %>">
        <button class="btn btn--success btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Approve User',
                        'Approve this user?',
                        () => document.getElementById('<%= formId %>').submit()
                        );">
          Approve
        </button>
      </form>
      <% } %>

      <!-- PROMOTE TO CO-ADMIN -->
      <% if ("student".equals(role) && "approved".equals(status)) {
        formId = "promoteForm_" + u.getUserId();
      %>
      <form id="<%= formId %>" action="<%= ctx %>/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="promote-coadmin">
        <input type="hidden" name="userId" value="<%= u.getUserId() %>">
        <button class="btn btn--accent btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Promote User',
                        'Promote this user to Co-Admin?',
                        () => document.getElementById('<%= formId %>').submit()
                        );">
          Promote
        </button>
      </form>
      <% } %>

      <!-- DEMOTE (ADMIN ONLY) -->
      <% if (isAdmin && "co-admin".equals(role) && !isSelf) {
        formId = "demoteForm_" + u.getUserId();
      %>
      <form id="<%= formId %>" action="<%= ctx %>/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="demote-student">
        <input type="hidden" name="userId" value="<%= u.getUserId() %>">
        <button class="btn btn--outline btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Demote User',
                        'Demote this co-admin to student?',
                        () => document.getElementById('<%= formId %>').submit()
                        );">
          Demote
        </button>
      </form>
      <% } %>

      <!-- DELETE -->
      <% if (!isSelf && (isAdmin || (isCoadmin && "student".equals(role)))) {
        formId = "deleteForm_" + u.getUserId();
      %>
      <form id="<%= formId %>" action="<%= ctx %>/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="userId" value="<%= u.getUserId() %>">
        <button class="btn btn--danger btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Delete User',
                        'Delete this user permanently?',
                        () => document.getElementById('<%= formId %>').submit()
                        );">
          Delete
        </button>
      </form>
      <% } %>

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
  <% } %>
  </tbody>
</table>

<br>
<a href="<%= ctx %>/admin/dashboard" class="btn btn--outline">← Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />