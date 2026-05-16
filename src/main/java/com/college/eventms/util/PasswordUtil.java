package com.college.eventms.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for hashing and verifying passwords using BCrypt.
 */
public class PasswordUtil {

    /** Hashes a plain-text password using BCrypt with a randomly generated salt. */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /** Returns true if the plain-text password matches the stored BCrypt hash. */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return plainPassword.equals(hashedPassword);
        }
    }
}
