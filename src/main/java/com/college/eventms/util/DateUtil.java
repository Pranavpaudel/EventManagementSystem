package com.college.eventms.util;

import java.time.LocalDate;

/**
 * Utility class for date-related checks used in event validation.
 */
public class DateUtil {

    /** Returns true if the given ISO date string (yyyy-MM-dd) is earlier than today. */
    public static boolean isPastDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr).isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
}
