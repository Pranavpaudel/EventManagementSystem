<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.college.eventms.entity.User" %>
<%
  /* Retrieve the logged-in user from the session (may be null) */
  User navUser = (User) session.getAttribute("user");
  String ctx   = request.getContextPath();

  /* Active-link detection: compare current URI to each link path */
  String currentURI = request.getRequestURI();
  String navRole = (navUser != null && navUser.getRole() != null) ? navUser.getRole().toLowerCase() : "";
%>

<!-- ===== NAVIGATION ===== -->
<nav class="site-nav">
  <ul class="site-nav__list">

  <% if (navUser == null) { %>
    <%-- ======= GUEST (logged-out) ======= --%>
    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/login") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/login">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
          <polyline points="10 17 15 12 10 7"/>
          <line x1="15" y1="12" x2="3" y2="12"/>
        </svg>
        Login
      </a>
    </li>
    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/register") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/register">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
          <circle cx="9" cy="7" r="4"/>
          <line x1="19" y1="8" x2="19" y2="14"/>
          <line x1="22" y1="11" x2="16" y2="11"/>
        </svg>
        Register
      </a>
    </li>

  <% } else if ("admin".equals(navRole) || "co-admin".equals(navRole)) { %>
    <%-- ======= ADMIN / CO-ADMIN ======= --%>
    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/admin/dashboard") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/admin/dashboard">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="3" width="7" height="7"/>
          <rect x="14" y="3" width="7" height="7"/>
          <rect x="14" y="14" width="7" height="7"/>
          <rect x="3" y="14" width="7" height="7"/>
        </svg>
        Dashboard
      </a>
    </li>
    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/admin/add-event") || currentURI.contains("/admin/edit-event") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/admin/add-event">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"/>
          <line x1="12" y1="8" x2="12" y2="16"/>
          <line x1="8" y1="12" x2="16" y2="12"/>
        </svg>
        Add Event
      </a>
    </li>

    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/admin/users") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/admin/users">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
          <circle cx="9" cy="7" r="4"/>
          <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
          <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
        </svg>
        Manage Users
      </a>
    </li>

    <%-- spacer pushes logout to the right --%>
    <li class="site-nav__spacer"></li>

    <li class="site-nav__item">
      <span class="site-nav__user-badge">
        <%= "admin".equals(navRole) ? "Admin" : "Co-Admin" %> &mdash; <%= navUser.getFullName() %>
      </span>
    </li>
    <li class="site-nav__item">
      <a class="site-nav__link site-nav__link--logout" href="<%= ctx %>/logout">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
          <polyline points="16 17 21 12 16 7"/>
          <line x1="21" y1="12" x2="9" y2="12"/>
        </svg>
        Logout
      </a>
    </li>

  <% } else { %>
    <%-- ======= REGULAR USER (student) ======= --%>
    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/user-dashboard") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/user-dashboard">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="3" width="7" height="7"/>
          <rect x="14" y="3" width="7" height="7"/>
          <rect x="14" y="14" width="7" height="7"/>
          <rect x="3" y="14" width="7" height="7"/>
        </svg>
        Dashboard
      </a>
    </li>
    <li class="site-nav__item">
      <a class="site-nav__link<%= currentURI.contains("/events") ? " site-nav__link--active" : "" %>" href="<%= ctx %>/events">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="4" width="18" height="18" rx="2"/>
          <line x1="16" y1="2" x2="16" y2="6"/>
          <line x1="8"  y1="2" x2="8"  y2="6"/>
          <line x1="3"  y1="10" x2="21" y2="10"/>
        </svg>
        View Events
      </a>
    </li>

    <%-- spacer pushes logout to the right --%>
    <li class="site-nav__spacer"></li>

    <li class="site-nav__item">
      <span class="site-nav__user-badge"><%= navUser.getFullName() %></span>
    </li>
    <li class="site-nav__item">
      <a class="site-nav__link site-nav__link--logout" href="<%= ctx %>/logout">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
          <polyline points="16 17 21 12 16 7"/>
          <line x1="21" y1="12" x2="9" y2="12"/>
        </svg>
        Logout
      </a>
    </li>
  <% } %>

  </ul>
</nav>
<main class="page-content">