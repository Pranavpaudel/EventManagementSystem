(function () {
  function markInvalid(el, msg) {
    el.style.borderColor = "#e53e3e";
    el.focus();
    alert(msg);
  }

  function clearBorders(form) {
    form.querySelectorAll("input, textarea, select").forEach(function (el) {
      el.style.borderColor = "";
    });
  }

  var registerForm = document.getElementById("registerForm");
  if (registerForm) {
    registerForm.addEventListener("submit", function (e) {
      clearBorders(registerForm);

      var name    = document.getElementById("fullName");
      var contact = document.getElementById("contact");
      var email   = document.getElementById("email");
      var pass    = document.getElementById("password");

      if (!name.value.trim()) {
        e.preventDefault(); markInvalid(name, "Full name is required."); return;
      }
      if (!/^[a-zA-Z ]+$/.test(name.value.trim())) {
        e.preventDefault(); markInvalid(name, "Name must contain only letters and spaces."); return;
      }
      if (!email.value.includes("@")) {
        e.preventDefault(); markInvalid(email, "Please enter a valid email address."); return;
      }
      if (!/^\d{7,15}$/.test(contact.value.trim())) {
        e.preventDefault(); markInvalid(contact, "Contact must be 7–15 digits only."); return;
      }
      if (pass.value.trim().length < 6) {
        e.preventDefault(); markInvalid(pass, "Password must be at least 6 characters."); return;
      }
    });
  }

  var addEventForm = document.getElementById("addEventForm");
  if (addEventForm) {
    addEventForm.addEventListener("submit", function (e) {
      clearBorders(addEventForm);

      var capacity  = document.getElementById("capacity");
      var eventDate = document.getElementById("eventDate");

      if (!capacity.value || parseInt(capacity.value) < 1) {
        e.preventDefault(); markInvalid(capacity, "Capacity must be at least 1."); return;
      }
      if (eventDate.value) {
        var today = new Date(); today.setHours(0, 0, 0, 0);
        if (new Date(eventDate.value) < today) {
          e.preventDefault(); markInvalid(eventDate, "Event date cannot be in the past."); return;
        }
      }
    });
  }
})();
