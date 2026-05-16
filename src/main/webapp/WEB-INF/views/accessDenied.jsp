<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<div class="error-page">
  <div class="error-page__icon">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <circle cx="12" cy="12" r="10"/>
      <line x1="15" y1="9" x2="9" y2="15"/>
      <line x1="9" y1="9" x2="15" y2="15"/>
    </svg>
  </div>
  <h1>403 &mdash; Access Denied</h1>
  <p>You do not have permission to view this page.</p>
  <c:choose>
    <c:when test="${not empty sessionScope.user}">
      <a href="${pageContext.request.contextPath}/user-dashboard" class="btn btn--primary">Back to Dashboard</a>
    </c:when>
    <c:otherwise>
      <a href="${pageContext.request.contextPath}/login" class="btn btn--primary">Go to Login</a>
    </c:otherwise>
  </c:choose>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
