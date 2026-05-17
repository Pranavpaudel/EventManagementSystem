package com.college.eventms.util;

import javax.servlet.http.Part;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Paths;

public class ImageUtil {

    private static String BASE_PATH;

    public static void init(String realPath) {
        BASE_PATH = Paths.get(realPath)
                .getParent().getParent()
                .resolve("uploads/events")
                .toString() + File.separator;
        new File(BASE_PATH).mkdirs();
        System.out.println("[ImageUtil] Upload folder resolved to: " + BASE_PATH);
    }

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

            new File(BASE_PATH).mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String uniqueName = timestamp + "_" + originalName;
            part.write(BASE_PATH + uniqueName);
            return uniqueName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) return;
        if (imagePath.equals("static/images/default-event.png")) return;

        File file = new File(BASE_PATH + imagePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String getUploadFolder() {
        return BASE_PATH;
    }
}
