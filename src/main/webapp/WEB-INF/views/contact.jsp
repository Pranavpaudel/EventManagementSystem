<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />
<jsp:include page="/WEB-INF/templates/nav.jsp" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- ===== Contact Content ===== -->
<h1 class="page-title">Contact Us</h1>

<c:if test="${not empty sessionScope.successMessage}">
  <div class="alert alert--success">${sessionScope.successMessage}</div>
  <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty error}">
  <div class="alert alert--danger">${error}</div>
</c:if>

<div class="card card--centered">
  <form action="${ctx}/contact" method="post">

    <div class="form-group">
      <label for="name">Name</label>
      <input id="name" class="form-control" type="text" name="name"
             placeholder="Your full name" required>
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input id="email" class="form-control" type="email" name="email"
             placeholder="Your email address" required>
    </div>

    <div class="form-group">
      <label for="message">Message</label>
      <textarea id="message" class="form-control" name="message"
                placeholder="Write your message here..." required></textarea>
    </div>

    <button type="submit" class="btn btn--primary" style="width:100%;">Send Message</button>
  </form>
</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
