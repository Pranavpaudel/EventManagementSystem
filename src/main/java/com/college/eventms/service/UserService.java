package com.college.eventms.service;

import com.college.eventms.dao.UserDAO;
import com.college.eventms.entity.User;
import com.college.eventms.util.PasswordUtil;
import com.college.eventms.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for user-related business logic — registration, authentication, and profile management.
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Validates and registers a new student account.
     *
     * @param profileImage pre-resolved image path (filename or default), never null
     * @return null on success, or a human-readable error message on failure.
     */
    public String registerUser(String fullName, String contact, String email, String password, String profileImage) {
        if (ValidationUtil.isNullOrEmpty(fullName) || ValidationUtil.isNullOrEmpty(contact)
                || ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
            return "All fields are required.";
        }
        if (!ValidationUtil.isAlphaOnly(fullName)) {
            return "Name must contain only letters and spaces.";
        }
        if (!ValidationUtil.isValidEmail(email)) {
            return "Please enter a valid email address.";
        }
        if (!ValidationUtil.isValidPhone(contact)) {
            return "Phone number must be 7–15 digits.";
        }
        if (password.trim().length() < 6) {
            return "Password must be at least 6 characters.";
        }
        try {
            if (userDAO.getUserByEmail(email) != null) {
                return "An account with this email already exists.";
            }
            if (userDAO.getUserByContact(contact) != null) {
                return "An account with this contact number already exists.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Registration failed. Please try again.";
        }

        User user = new User();
        user.setFullName(fullName.trim());
        user.setContact(contact.trim());
        user.setEmail(email.trim());
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setRole("student");
        user.setStatus("pending");
        user.setProfileImage(profileImage);

        return userDAO.registerUser(user) ? null : "Registration failed. Please try again.";
    }

    /** Finds a user by contact number or email; returns null if not found. */
    public User findByIdentifier(String identifier) {
        try {
            User user = userDAO.getUserByContact(identifier);
            return user != null ? user : userDAO.getUserByEmail(identifier);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Returns true if the plain-text password matches the stored hash. */
    public boolean verifyPassword(String plain, String hashed) {
        return PasswordUtil.verifyPassword(plain, hashed);
    }

    /**
     * Authenticates a user by identifier and password, checking approval status.
     *
     * @return the User on success, or null if credentials are invalid or account not approved.
     */
    public User authenticateUser(String identifier, String password) {
        User user = findByIdentifier(identifier);
        if (user == null) return null;
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) return null;
        if (!user.getStatus().equalsIgnoreCase("approved")) return null;
        return user;
    }

    /**
     * Validates and updates a user's profile fields.
     *
     * @return null on success, or a human-readable error message on failure.
     */
    public String updateProfile(User user) {
        if (ValidationUtil.isNullOrEmpty(user.getFullName())
                || ValidationUtil.isNullOrEmpty(user.getContact())
                || ValidationUtil.isNullOrEmpty(user.getEmail())) {
            return "All fields are required.";
        }
        if (!ValidationUtil.isAlphaOnly(user.getFullName())) {
            return "Name must contain only letters and spaces.";
        }
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            return "Please enter a valid email address.";
        }
        if (!ValidationUtil.isValidPhone(user.getContact())) {
            return "Phone number must be 7–15 digits.";
        }
        return userDAO.updateUser(user) ? null : "Update failed. Please try again.";
    }

    /** Returns all users in the system. */
    public List<User> getAllUsers()                              { return userDAO.getAllUsers(); }

    /** Returns all users with pending approval status. */
    public List<User> getPendingUsers()                         { return userDAO.getPendingUsers(); }

    /** Approves the specified user account. */
    public boolean approveUser(int userId)                      { return userDAO.approveUser(userId); }

    /** Deletes the specified user account. */
    public boolean deleteUser(int userId)                       { return userDAO.deleteUser(userId); }

    /** Updates the role of the specified user. */
    public boolean updateUserRole(int userId, String role)      { return userDAO.updateUserRole(userId, role); }
}
