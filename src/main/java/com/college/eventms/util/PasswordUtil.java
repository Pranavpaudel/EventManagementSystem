package com.college.eventms.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for hashing and verifying passwords using BCrypt.
 */
public class PasswordUtil {

    /**
     * Hashes a plain-text password using BCrypt with a randomly generated salt.
     *
     * @param plainPassword the raw password entered by the user; must not be null
     * @return a BCrypt hash string safe for database storage
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Checks whether a plain-text password matches a stored BCrypt hash.
     * Falls back to a plain equality check if the hash is not a valid BCrypt string.
     *
     * @param plainPassword  the raw password to verify
     * @param hashedPassword the BCrypt hash retrieved from the database
     * @return {@code true} if the password matches the hash, {@code false} otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return plainPassword.equals(hashedPassword);
        }
    }
}
