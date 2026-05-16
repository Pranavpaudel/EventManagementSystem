<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== Login Page Content ===== -->
<div class="auth-wrapper">
  <div class="auth-card">
    <h1 class="auth-card__title">Sign In</h1>

    <c:if test="${not empty error}">
    <div class="alert alert--danger">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">

      <div class="form-group">
        <label for="identifier">Email or Contact</label>
        <input id="identifier" class="form-control" type="text" name="identifier"
               placeholder="Enter your email or contact number"
               value="${cookie.remember_identifier.value}" required>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input id="password" class="form-control" type="password" name="password"
               placeholder="Enter your password" required>
      </div>

      <div class="form-check">
        <input id="rememberMe" type="checkbox" name="rememberMe" value="true"
               ${not empty cookie.remember_identifier.value ? 'checked' : ''}>
        <label for="rememberMe">Remember me for 30 days</label>
      </div>

      <button type="submit" class="btn btn--primary" style="width:100%;margin-top:.5rem;">Login</button>
    </form>

    <p class="auth-card__footer">
      Don't have an account?
      <a href="${pageContext.request.contextPath}/register">Register here</a>
    </p>
  </div>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
