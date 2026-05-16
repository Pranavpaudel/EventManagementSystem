<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>

<!-- ===== PAGE CONTENT END ===== -->
</main>



<!-- ===== FOOTER ===== -->
<jsp:useBean id="cal" class="java.util.GregorianCalendar" scope="page" />
<footer class="site-footer">
  &copy; ${cal.get(1)} Event Management System &mdash; Informatics College of Computing.
  All rights reserved.
</footer>
<!-- ===== Confirmation Modal (hidden by default) ===== -->
<div id="confirmModal" class="confirm-overlay hidden">
  <div class="confirm-box">
    <h3 id="confirmTitle">Confirm Action</h3>
    <p id="confirmMessage">Are you sure?</p>

    <div class="confirm-actions">
      <button type="button" id="confirmCancel" class="btn btn--outline">
        Cancel
      </button>
      <button type="button" id="confirmOk" class="btn btn--danger">
        Confirm
      </button>
    </div>
  </div>
</div>

<script>
  /* Replace broken images with a gradient SVG placeholder */
  document.querySelectorAll('img').forEach(function(img) {
    img.addEventListener('error', function() {
      this.onerror = null;
      this.src = 'data:image/svg+xml;utf8,%3Csvg xmlns%3D%22http%3A//www.w3.org/2000/svg%22 width%3D%22100%25%22 height%3D%22100%25%22%3E%3Cdefs%3E%3ClinearGradient id%3D%22g%22 x1%3D%220%22 y1%3D%220%22 x2%3D%221%22 y2%3D%221%22%3E%3Cstop offset%3D%220%22 stop-color%3D%22%23E2E8F0%22/%3E%3Cstop offset%3D%221%22 stop-color%3D%22%23F1F5F9%22/%3E%3C/linearGradient%3E%3C/defs%3E%3Crect width%3D%22100%25%22 height%3D%22100%25%22 fill%3D%22url(%23g)%22/%3E%3C/svg%3E';
    });
  });

  let confirmCallback = null;

  function showConfirm(title, message, onConfirm) {
    document.getElementById("confirmTitle").innerText = title;
    document.getElementById("confirmMessage").innerText = message;

    document.getElementById("confirmModal").classList.remove("hidden");
    confirmCallback = onConfirm;
  }

  document.getElementById("confirmCancel").onclick = function () {
    document.getElementById("confirmModal").classList.add("hidden");
    confirmCallback = null;
  };

  document.getElementById("confirmOk").onclick = function () {
    document.getElementById("confirmModal").classList.add("hidden");
    if (confirmCallback) confirmCallback();
    confirmCallback = null;
  };
</script>

<script src="${pageContext.request.contextPath}/static/js/validation.js"></script>
</body>
</html>
