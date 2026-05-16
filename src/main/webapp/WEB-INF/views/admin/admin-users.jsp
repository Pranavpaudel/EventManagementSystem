<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="currentUser" value="${sessionScope.user}" />
<c:set var="currentUserId" value="${empty currentUser ? -1 : currentUser.userId}" />
<c:set var="myRole" value="${empty currentUser ? '' : currentUser.role.toLowerCase()}" />
<c:set var="isAdmin" value="${myRole eq 'admin'}" />
<c:set var="isCoadmin" value="${myRole eq 'co-admin'}" />
<c:set var="allUsers" value="${requestScope.allUsers}" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<h1 class="page-title">Manage Users</h1>

<c:if test="${not empty sessionScope.successMessage}">
<div class="alert alert--success">${sessionScope.successMessage}</div>
<c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.failMessage}">
<div class="alert alert--danger">${sessionScope.failMessage}</div>
<c:remove var="failMessage" scope="session"/>
</c:if>

<table class="data-table">
  <thead>
  <tr>
    <th>Photo</th>
    <th>Full Name</th>
    <th>Email</th>
    <th>Contact</th>
    <th>Role</th>
    <th>Status</th>
    <th>Actions</th>
  </tr>
  </thead>

  <tbody>
  <c:choose>
    <c:when test="${not empty allUsers}">
      <c:forEach var="u" items="${allUsers}">
      <c:set var="isSelf" value="${u.userId == currentUserId}" />
      <c:set var="role" value="${u.role.toLowerCase()}" />
      <c:set var="status" value="${u.status.toLowerCase()}" />
  <tr>
    <td>
      <c:choose>
        <c:when test="${u.profileImage.startsWith('static/')}">
          <img src="${ctx}/${u.profileImage}" alt="" width="40" height="40"
               style="border-radius:50%;object-fit:cover;">
        </c:when>
        <c:otherwise>
          <img src="${ctx}/profile-uploads/${u.profileImage}" alt="" width="40" height="40"
               style="border-radius:50%;object-fit:cover;">
        </c:otherwise>
      </c:choose>
    </td>
    <td>${u.fullName}</td>
    <td>${u.email}</td>
    <td>${u.contact}</td>
    <td>${u.role}</td>
    <td>
    <span class="status-badge status-badge--${status}">
        ${status}
    </span>
    </td>

    <td>

      <!-- APPROVE -->
      <c:if test="${status eq 'pending'}">
      <form id="approveForm_${u.userId}" action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="approve">
        <input type="hidden" name="userId" value="${u.userId}">
        <button class="btn btn--success btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Approve User',
                        'Approve this user?',
                        () => document.getElementById('approveForm_${u.userId}').submit()
                        );">
          Approve
        </button>
      </form>
      </c:if>

      <!-- PROMOTE TO CO-ADMIN -->
      <c:if test="${role eq 'student' and status eq 'approved'}">
      <form id="promoteForm_${u.userId}" action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="promote-coadmin">
        <input type="hidden" name="userId" value="${u.userId}">
        <button class="btn btn--accent btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Promote User',
                        'Promote this user to Co-Admin?',
                        () => document.getElementById('promoteForm_${u.userId}').submit()
                        );">
          Promote
        </button>
      </form>
      </c:if>

      <!-- DEMOTE (ADMIN ONLY) -->
      <c:if test="${isAdmin and role eq 'co-admin' and not isSelf}">
      <form id="demoteForm_${u.userId}" action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="demote-student">
        <input type="hidden" name="userId" value="${u.userId}">
        <button class="btn btn--outline btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Demote User',
                        'Demote this co-admin to student?',
                        () => document.getElementById('demoteForm_${u.userId}').submit()
                        );">
          Demote
        </button>
      </form>
      </c:if>

      <!-- DELETE -->
      <c:if test="${not isSelf and (isAdmin or (isCoadmin and role eq 'student'))}">
      <form id="deleteForm_${u.userId}" action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="userId" value="${u.userId}">
        <button class="btn btn--danger btn--sm"
                onclick="event.preventDefault();
                        showConfirm(
                        'Delete User',
                        'Delete this user permanently?',
                        () => document.getElementById('deleteForm_${u.userId}').submit()
                        );">
          Delete
        </button>
      </form>
      </c:if>

      <c:if test="${isSelf}">
      <span class="status-badge status-badge--self">You</span>
      </c:if>

    </td>
  </tr>
      </c:forEach>
    </c:when>
    <c:otherwise>
  <tr class="empty-row">
    <td colspan="7">No users found.</td>
  </tr>
    </c:otherwise>
  </c:choose>
  </tbody>
</table>

<br>
<a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn--outline">← Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
