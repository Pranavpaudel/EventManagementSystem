package com.college.eventms.util;

import java.util.regex.Pattern;

/**
 * Utility class providing input validation helpers used across servlets and services.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{7,15}$");

    private static final Pattern ALPHA_PATTERN =
            Pattern.compile("^[a-zA-Z ]+$");

    /** Returns true if the value is null or contains only whitespace. */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /** Returns true if the email matches a basic RFC-style pattern. */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /** Returns true if the phone number contains 7–15 digits. */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /** Returns true if the value contains only letters and spaces. */
    public static boolean isAlphaOnly(String value) {
        return value != null && ALPHA_PATTERN.matcher(value.trim()).matches();
    }
}
