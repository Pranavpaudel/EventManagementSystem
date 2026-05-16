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

<script>
  /* ===== Toast System ===== */
  var _toastContainer = document.createElement('div');
  _toastContainer.id = 'toast-container';
  document.body.appendChild(_toastContainer);

  var _toastIcons = {
    danger:  '<svg class="toast__icon" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/></svg>',
    success: '<svg class="toast__icon" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/></svg>'
  };

  function showToast(message, type) {
    type = type || 'danger';
    var toast = document.createElement('div');
    toast.className = 'toast toast--' + type;
    toast.innerHTML = (_toastIcons[type] || '') +
      '<span class="toast__msg">' + message + '</span>' +
      '<button class="toast__close" aria-label="Close">&times;</button>';
    _toastContainer.appendChild(toast);

    function dismiss() {
      toast.classList.add('toast--exit');
      setTimeout(function() { if (toast.parentNode) toast.parentNode.removeChild(toast); }, 220);
    }

    toast.querySelector('.toast__close').addEventListener('click', dismiss);
    setTimeout(dismiss, 4500);
  }

  /* ===== Enhance inline .alert elements with icon + close + auto-dismiss ===== */
  var _alertIcons = {
    danger:  '<svg class="alert__icon" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/></svg>',
    success: '<svg class="alert__icon" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/></svg>',
    info:    '<svg class="alert__icon" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/></svg>'
  };

  document.querySelectorAll('.alert').forEach(function(el) {
    var type = el.classList.contains('alert--success') ? 'success'
             : el.classList.contains('alert--danger')  ? 'danger'
             : 'info';
    var text = el.innerHTML;
    el.innerHTML = (_alertIcons[type] || '') +
      '<span class="alert__msg">' + text + '</span>' +
      '<button class="alert__close" aria-label="Close">&times;</button>';

    el.querySelector('.alert__close').addEventListener('click', function() {
      el.style.transition = 'opacity .2s';
      el.style.opacity = '0';
      setTimeout(function() { if (el.parentNode) el.parentNode.removeChild(el); }, 200);
    });

    setTimeout(function() {
      if (el.parentNode) {
        el.style.transition = 'opacity .4s';
        el.style.opacity = '0';
        setTimeout(function() { if (el.parentNode) el.parentNode.removeChild(el); }, 400);
      }
    }, 5000);
  });
</script>
<script src="${pageContext.request.contextPath}/static/js/validation.js"></script>
</body>
</html>
