<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<div class="error-page">
  <div class="error-page__icon">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <circle cx="12" cy="12" r="10"/>
      <line x1="12" y1="8" x2="12" y2="12"/>
      <line x1="12" y1="16" x2="12.01" y2="16"/>
    </svg>
  </div>
  <h1>404 &mdash; Page Not Found</h1>
  <p>The page you are looking for does not exist.</p>
  <a href="${pageContext.request.contextPath}/" class="btn btn--primary">Go to Home</a>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
