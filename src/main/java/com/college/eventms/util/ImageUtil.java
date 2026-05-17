package com.college.eventms.util;

import javax.servlet.http.Part;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for uploading and deleting event images.
 * The upload folder is a fixed absolute path to the project's uploads/events directory.
 */
public class ImageUtil {

    private static String uploadFolder =
            "C:/Users/user/Desktop/Sem2/java/JavaAPT/EventManagementSystem/uploads/events/";

    public static void setUploadFolder(String path) {
        uploadFolder = path;
    }

    /**
     * Saves the submitted image file to the event-uploads folder using a
     * {@code <timestamp>_<originalName>} filename to guarantee uniqueness.
     *
     * @param part the {@code multipart/form-data} file part from the servlet request
     * @return the generated filename (e.g. {@code 1715000000000_photo.jpg}) on success,
     *         or {@code null} if the part is absent, empty, or not a supported image type
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

            File uploadDir = new File(uploadFolder);
            uploadDir.mkdirs();

            System.out.println("[ImageUtil] Upload path: " + uploadDir.getAbsolutePath());

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String uniqueName = timestamp + "_" + originalName;
            part.write(uploadFolder + uniqueName);
            return uniqueName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes an image file from the event-uploads folder.
     * No-ops silently if {@code imagePath} is null, blank, or the shared default placeholder.
     *
     * @param imagePath the filename stored in the database (not an absolute path)
     */
    public static void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) return;
        if (imagePath.equals("static/images/default-event.png")) return;

        File file = new File(uploadFolder + imagePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Returns the absolute filesystem path of the event-uploads folder (includes trailing separator).
     *
     * @return the current upload folder path
     */
    public static String getUploadFolder() {
        return uploadFolder;
    }
}
