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

    /**
     * Returns {@code true} if the value is {@code null} or contains only whitespace.
     *
     * @param value the string to test
     * @return {@code true} if blank or null
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Returns {@code true} if the email address matches a basic {@code local@domain.tld} pattern.
     *
     * @param email the email address to validate
     * @return {@code true} if the format is valid, {@code false} if null or malformed
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Returns {@code true} if the phone number consists solely of 7–15 digits.
     *
     * @param phone the phone number string to validate
     * @return {@code true} if valid, {@code false} if null or out of range
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Returns {@code true} if the value contains only letters (a-z, A-Z) and spaces.
     *
     * @param value the string to test
     * @return {@code true} if the value is alphabetic (with spaces), {@code false} otherwise
     */
    public static boolean isAlphaOnly(String value) {
        return value != null && ALPHA_PATTERN.matcher(value.trim()).matches();
    }
}
