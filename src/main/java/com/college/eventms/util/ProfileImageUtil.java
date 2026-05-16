package com.college.eventms.util;

import javax.servlet.http.Part;
import java.io.File;

/**
 * Utility class for uploading and deleting user profile images stored outside the webapp directory.
 */
public class ProfileImageUtil {

    public static final String UPLOAD_FOLDER =
            System.getProperty("user.home") + File.separator + "profile-uploads" + File.separator;

    /**
     * Saves an uploaded profile image part with a timestamp-prefixed filename.
     *
     * @return the unique filename on success, or null if no valid image was submitted.
     */
    public static String uploadImage(Part part) {
        try {
            if (part == null) return null;

            String originalName = part.getSubmittedFileName();
            if (originalName == null || originalName.trim().isEmpty() || part.getSize() == 0) {
                return null;
            }

            String lower = originalName.toLowerCase();
            if (!lower.endsWith(".jpg") && !lower.endsWith(".jpeg") && !lower.endsWith(".png")) {
                return null;
            }

            File uploadDir = new File(UPLOAD_FOLDER);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uniqueName = System.currentTimeMillis() + "_" + originalName;
            part.write(UPLOAD_FOLDER + uniqueName);
            return uniqueName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a profile image file from the upload folder.
     * Does nothing if the path is null, empty, or the shared default avatar.
     */
    public static void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) return;
        if (imagePath.equals("static/images/default-avatar.png")) return;

        File file = new File(UPLOAD_FOLDER + imagePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /** Returns the absolute path to the profile upload folder. */
    public static String getUploadFolder() {
        return UPLOAD_FOLDER;
    }
}
