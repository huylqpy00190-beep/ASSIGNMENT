package com.abcnews.utils;


import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.tomcat.jakartaee.commons.io.FilenameUtils;

public class UploadUtils {
    public static String saveImage(Part part, String uploadPath) throws Exception {
        if (part == null || part.getSize() == 0) return null;
        String submitted = part.getSubmittedFileName();
        String ext = FilenameUtils.getExtension(submitted);
        String filename = UUID.randomUUID().toString() + (ext.isEmpty() ? "" : "." + ext);
        File uploads = new File(uploadPath);
        if (!uploads.exists()) uploads.mkdirs();
        File file = new File(uploads, filename);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return filename;
    }
}
