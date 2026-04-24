<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<!-- ===== Landing Page Content ===== -->
<section class="landing-hero">
  <h1 class="landing-hero__title">Welcome to <span>Event Management</span></h1>
  <p class="landing-hero__sub">
    Discover, organize, and manage college events all in one place.
    Sign in to explore upcoming events or register to get started.
  </p>
  <div class="landing-actions">
    <a href="<%= request.getContextPath() %>/login" class="btn btn--primary">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
           fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
        <polyline points="10 17 15 12 10 7"/>
        <line x1="15" y1="12" x2="3" y2="12"/>
      </svg>
      Login
    </a>
    <a href="<%= request.getContextPath() %>/register" class="btn btn--outline">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
           fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
        <circle cx="9" cy="7" r="4"/>
        <line x1="19" y1="8" x2="19" y2="14"/>
        <line x1="22" y1="11" x2="16" y2="11"/>
      </svg>
      Register
    </a>
  </div>
</section>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
