package com.college.eventms.controller;

import com.college.eventms.util.ImageUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Streams uploaded event images from the server's local upload folder to the browser.
 */
@WebServlet("/event-uploads/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String filename = pathInfo.substring(1);
        File file = new File(ImageUtil.getUploadFolder() + filename);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String lower = filename.toLowerCase();
        response.setContentType(lower.endsWith(".png") ? "image/png" : "image/jpeg");

        Files.copy(file.toPath(), response.getOutputStream());
    }
}
