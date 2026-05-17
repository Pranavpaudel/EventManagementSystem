package com.college.eventms.controller;

import com.college.eventms.util.ProfileImageUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Streams uploaded user profile images from the server's local profile-uploads folder to the browser.
 */
@WebServlet("/profile-uploads/*")
public class ProfileImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ProfileImageUtil.init(getServletContext().getRealPath("/"));

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String filename = pathInfo.substring(1);
        File file = new File(ProfileImageUtil.getUploadFolder() + filename);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String lower = filename.toLowerCase();
        response.setContentType(lower.endsWith(".png") ? "image/png" : "image/jpeg");

        Files.copy(file.toPath(), response.getOutputStream());
    }
}
