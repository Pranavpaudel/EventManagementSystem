package com.college.eventms.util;

import javax.servlet.http.Part;
import java.io.File;

/**
 * Utility class for uploading and deleting event images stored outside the webapp directory.
 */
public class ImageUtil {

    public static final String UPLOAD_FOLDER =
            System.getProperty("user.home") + File.separator + "event-uploads" + File.separator;

    /**
     * Saves an uploaded image part to the upload folder with a timestamp-prefixed filename.
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
     * Deletes an image file from the upload folder.
     * Does nothing if the path is null, empty, or the shared default image.
     */
    public static void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) return;
        if (imagePath.equals("static/images/default-event.png")) return;

        File file = new File(UPLOAD_FOLDER + imagePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /** Returns the absolute path to the upload folder. */
    public static String getUploadFolder() {
        return UPLOAD_FOLDER;
    }
}
