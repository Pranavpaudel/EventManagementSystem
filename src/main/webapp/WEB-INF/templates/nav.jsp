<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="navUser" value="${sessionScope.user}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="currentURI" value="${pageContext.request.requestURI}" />
<c:set var="navRole" value="${empty navUser or empty navUser.role ? '' : navUser.role.toLowerCase()}" />

<!-- ===== NAVIGATION ===== -->
<nav class="site-nav">
  <ul class="site-nav__list">

    <c:choose>
      <c:when test="${empty navUser}">
        <%-- ======= GUEST (logged-out) ======= --%>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/login') ? ' site-nav__link--active' : ''}" href="${ctx}/login">Login</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/register') ? ' site-nav__link--active' : ''}" href="${ctx}/register">Register</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/about') ? ' site-nav__link--active' : ''}" href="${ctx}/about">About</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/contact') ? ' site-nav__link--active' : ''}" href="${ctx}/contact">Contact</a>
        </li>
      </c:when>

      <c:when test="${navRole eq 'admin' or navRole eq 'co-admin'}">
        <%-- ======= ADMIN / CO-ADMIN ======= --%>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/admin/dashboard') ? ' site-nav__link--active' : ''}" href="${ctx}/admin/dashboard">Dashboard</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/admin/add-event') or currentURI.contains('/admin/edit-event') ? ' site-nav__link--active' : ''}" href="${ctx}/admin/add-event">Add Event</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/admin/users') ? ' site-nav__link--active' : ''}" href="${ctx}/admin/users">Users</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/admin/reports') ? ' site-nav__link--active' : ''}" href="${ctx}/admin/reports">Reports</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/about') ? ' site-nav__link--active' : ''}" href="${ctx}/about">About</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/contact') ? ' site-nav__link--active' : ''}" href="${ctx}/contact">Contact</a>
        </li>

        <li class="site-nav__spacer"></li>

        <li class="site-nav__item">
      <span class="site-nav__user-badge">
        <c:choose>
          <c:when test="${empty navUser.profileImage}">
            <img src="${ctx}/static/images/default-avatar.png" alt="">
          </c:when>
          <c:when test="${navUser.profileImage.startsWith('static/')}">
            <img src="${ctx}/${navUser.profileImage}" alt="">
          </c:when>
          <c:otherwise>
            <img src="${ctx}/profile-uploads/${navUser.profileImage}" alt="">
          </c:otherwise>
        </c:choose>
        ${navUser.fullName}
      </span>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link site-nav__link--logout" href="${ctx}/logout">Logout</a>
        </li>
      </c:when>

      <c:otherwise>
        <%-- ======= REGULAR USER (student) ======= --%>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/user-dashboard') ? ' site-nav__link--active' : ''}" href="${ctx}/user-dashboard">Dashboard</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/events') ? ' site-nav__link--active' : ''}" href="${ctx}/events">Events</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/events/past') ? ' site-nav__link--active' : ''}" href="${ctx}/events/past">Past Events</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/my-bookings') ? ' site-nav__link--active' : ''}" href="${ctx}/my-bookings">Bookings</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/wishlist') ? ' site-nav__link--active' : ''}" href="${ctx}/wishlist">Wishlist</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/profile') ? ' site-nav__link--active' : ''}" href="${ctx}/profile">Profile</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/about') ? ' site-nav__link--active' : ''}" href="${ctx}/about">About</a>
        </li>
        <li class="site-nav__item">
          <a class="site-nav__link${currentURI.contains('/contact') ? ' site-nav__link--active' : ''}" href="${ctx}/contact">Contact</a>
        </li>

        <li class="site-nav__spacer"></li>

        <li class="site-nav__item">
      <span class="site-nav__user-badge" >${navUser.fullName}
      </span>
        </li>
        
        <li class="site-nav__item">
          <c:choose>
            <c:when test="${empty navUser.profileImage}">
              <img class="site-nav__avatar" src="${ctx}/static/images/default-avatar.png" alt="" style="width:28px!important;height:28px!important;min-width:28px!important;max-width:28px!important;min-height:28px!important;max-height:28px!important;border-radius:50%!important;object-fit:cover!important;display:inline-block!important;flex-shrink:0!important;border:none!important;outline:none!important;box-shadow:none!important;">
            </c:when>
            <c:when test="${navUser.profileImage.startsWith('static/')}">
              <img class="site-nav__avatar" src="${ctx}/${navUser.profileImage}" alt="" style="width:28px!important;height:28px!important;min-width:28px!important;max-width:28px!important;min-height:28px!important;max-height:28px!important;border-radius:50%!important;object-fit:cover!important;display:inline-block!important;flex-shrink:0!important;border:none!important;outline:none!important;box-shadow:none!important;">
            </c:when>
            <c:otherwise>
              <img class="site-nav__avatar" src="${ctx}/profile-uploads/${navUser.profileImage}" alt="" style="width:28px!important;height:28px!important;min-width:28px!important;max-width:28px!important;min-height:28px!important;max-height:28px!important;border-radius:50%!important;object-fit:cover!important;display:inline-block!important;flex-shrink:0!important;border:none!important;outline:none!important;box-shadow:none!important;">
            </c:otherwise>
          </c:choose>
        </li>

        <li class="site-nav__item">
          <a class="site-nav__link site-nav__link--logout" href="${ctx}/logout">Logout</a>
        </li>
      </c:otherwise>
    </c:choose>

  </ul>
</nav>
<main class="page-content">