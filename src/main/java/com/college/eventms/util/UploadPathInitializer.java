package com.college.eventms.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Initializes upload folder paths to uploads/event-uploads and uploads/profile-uploads
 * inside the deployed webapp, so images land in the project's uploads directory.
 */
@WebListener
public class UploadPathInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String eventUploads   = sce.getServletContext().getRealPath("/uploads/event-uploads")   + File.separator;
        String profileUploads = sce.getServletContext().getRealPath("/uploads/profile-uploads") + File.separator;

        new File(eventUploads).mkdirs();
        new File(profileUploads).mkdirs();

        ImageUtil.setUploadFolder(eventUploads);
        ProfileImageUtil.setUploadFolder(profileUploads);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
