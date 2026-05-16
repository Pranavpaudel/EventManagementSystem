package com.college.eventms.util;

import java.time.LocalDate;

/**
 * Utility class for date-related checks used in event validation.
 */
public class DateUtil {

    /**
     * Returns {@code true} if the given date string represents a date strictly before today.
     * Returns {@code false} if the string is null, empty, or cannot be parsed.
     *
     * @param dateStr an ISO-8601 date string in {@code yyyy-MM-dd} format
     * @return {@code true} if the date is in the past, {@code false} otherwise
     */
    public static boolean isPastDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr).isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
}
