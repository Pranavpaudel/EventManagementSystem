<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="u"   value="${sessionScope.user}" />

<!-- ===== Profile Content ===== -->
<h1 class="page-title">My Profile</h1>

<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert--success">${sessionScope.successMessage}</div>
  <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.failMessage}">
  <div class="alert alert--danger">${sessionScope.failMessage}</div>
  <c:remove var="failMessage" scope="session"/>
</c:if>

<div class="card card--centered">

  <div style="text-align:center;margin-bottom:1.25rem;">
    <c:choose>
      <c:when test="${u.profileImage.startsWith('static/')}">
        <img src="${ctx}/${u.profileImage}" alt="Profile Picture"
             style="width:100px;height:100px;border-radius:50%;object-fit:cover;border:2px solid #0D9488;">
      </c:when>
      <c:otherwise>
        <img src="${ctx}/profile-uploads/${u.profileImage}" alt="Profile Picture"
             style="width:100px;height:100px;border-radius:50%;object-fit:cover;border:2px solid #0D9488;">
      </c:otherwise>
    </c:choose>
  </div>

  <form action="${ctx}/profile" method="post" enctype="multipart/form-data">

    <div class="form-group">
      <label for="profileImage">Change Profile Picture</label>
      <input id="profileImage" class="form-control" type="file" name="profileImage"
             accept=".jpg,.jpeg,.png">
    </div>

    <div class="form-group">
      <label for="fullName">Full Name</label>
      <input id="fullName" class="form-control" type="text" name="fullName"
             value="${u.fullName}" required>
    </div>

    <div class="form-group">
      <label for="contact">Contact Number</label>
      <input id="contact" class="form-control" type="text" name="contact"
             value="${u.contact}" required>
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input id="email" class="form-control" type="email" name="email"
             value="${u.email}" required>
    </div>

    <div class="form-group">
      <label for="role">Role</label>
      <input id="role" class="form-control" type="text" value="${u.role}" disabled>
    </div>

    <div class="form-group">
      <label for="status">Account Status</label>
      <input id="status" class="form-control" type="text" value="${u.status}" disabled>
    </div>

    <button type="submit" class="btn btn--primary" style="width:100%;">Update Profile</button>
  </form>
</div>

<br>
<a href="${ctx}/user-dashboard" class="btn btn--outline">&larr; Back to Dashboard</a>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
