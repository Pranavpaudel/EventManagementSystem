<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<!-- ===== Registration Success Content ===== -->
<div class="success-page">
  <div class="success-page__icon">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none"
         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
      <polyline points="20 6 9 17 4 12"/>
    </svg>
  </div>
  <h2>Registration Successful!</h2>
  <p>Your account has been created and is pending approval by an administrator.</p>
  <a href="<%= request.getContextPath() %>/login" class="btn btn--primary">Go to Login</a>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
