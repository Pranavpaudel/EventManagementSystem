<%@ page contentType="text/html;charset=UTF-8" %>

<!-- ===== PAGE CONTENT END ===== -->
</main>



<!-- ===== FOOTER ===== -->
<footer class="site-footer">
  &copy; <%= java.time.Year.now().getValue() %> Event Management System &mdash; Informatics College of Computing.
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

</body>
</html>
