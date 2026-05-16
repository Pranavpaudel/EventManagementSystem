<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== Register Page Content ===== -->
<div class="auth-wrapper">
  <div class="auth-card">
    <h1 class="auth-card__title">Create Account</h1>

    <c:if test="${not empty error}">
      <div class="alert alert--danger">${error}</div>
    </c:if>

    <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">

      <div class="form-group">
        <label for="fullName">Full Name</label>
        <input id="fullName" class="form-control" type="text" name="fullName"
               placeholder="Enter your full name" required>
      </div>

      <div class="form-group">
        <label for="contact">Contact</label>
        <input id="contact" class="form-control" type="text" name="contact"
               placeholder="Enter your contact number" required>
      </div>

      <div class="form-group">
        <label for="email">Email</label>
        <input id="email" class="form-control" type="email" name="email"
               placeholder="Enter your email address" required>
      </div>

      <div class="form-group">
        <label for="profileImage">Profile Picture</label>
        <input id="profileImage" class="form-control" type="file" name="profileImage"
               accept=".jpg,.jpeg,.png">
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input id="password" class="form-control" type="password" name="password"
               placeholder="Create a password" required>
      </div>

      <button type="submit" class="btn btn--primary" style="width:100%;margin-top:.5rem;">Register</button>
    </form>

    <p class="auth-card__footer">
      Already have an account?
      <a href="${pageContext.request.contextPath}/login">Sign in</a>
    </p>
  </div>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
