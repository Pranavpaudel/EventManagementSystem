package com.college.eventms.util;

import com.college.eventms.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class for reading and checking the authenticated user from the HTTP session.
 *
 * <p>All servlets store the logged-in {@link User} under the session attribute {@code "user"}.
 * This class centralises that access so the repeated null-safe boilerplate does not have to be
 * duplicated across every servlet.</p>
 */
public class SessionUtil {

    /** Session attribute key under which the authenticated User object is stored. */
    private static final String USER_KEY = "user";

    private SessionUtil() {}

    /**
     * Returns the {@link User} stored in the current session, or {@code null} if there is
     * no active session or no user has been authenticated yet.
     *
     * @param request the incoming HTTP request
     * @return the authenticated {@link User}, or {@code null} if not logged in
     */
    public static User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute(USER_KEY);
    }

    /**
     * Returns {@code true} if a user is currently authenticated — i.e. the session exists
     * and holds a non-null {@link User} object.
     *
     * @param request the incoming HTTP request
     * @return {@code true} if a user is logged in, {@code false} otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUserFromSession(request) != null;
    }

    /**
     * Returns {@code true} if the authenticated user holds the {@code admin} or
     * {@code co-admin} role.
     *
     * @param request the incoming HTTP request
     * @return {@code true} if the current user is an admin or co-admin;
     *         {@code false} if not logged in or has a different role
     */
    public static boolean isAdmin(HttpServletRequest request) {
        User user = getUserFromSession(request);
        if (user == null) return false;
        String role = user.getRole();
        return role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("co-admin");
    }

    /**
     * Returns {@code true} if the authenticated user holds the {@code student} role.
     *
     * @param request the incoming HTTP request
     * @return {@code true} if the current user is a student;
     *         {@code false} if not logged in or has a different role
     */
    public static boolean isStudent(HttpServletRequest request) {
        User user = getUserFromSession(request);
        if (user == null) return false;
        return user.getRole().equalsIgnoreCase("student");
    }
}
