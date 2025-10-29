package com.abcnews.utils;

import jakarta.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class UploadUtils {
    public static String saveImage(Part part, String uploadPath) throws Exception {
        if (part == null || part.getSize() == 0) return null;

        String submitted = part.getSubmittedFileName();
        if (submitted == null) return null;

        String ext = FilenameUtils.getExtension(submitted).toLowerCase();
        if (!ext.matches("jpg|jpeg|png|gif|webp")) {
            throw new IllegalArgumentException("Chỉ chấp nhận file ảnh (jpg, png, gif, webp)");
        }

        // Nếu getRealPath trả về null (Tomcat deploy WAR)
        if (uploadPath == null) {
            uploadPath = System.getProperty("user.home") + File.separator + "uploads";
        }

        String filename = UUID.randomUUID().toString() + "." + ext;

        File uploads = new File(uploadPath);
        if (!uploads.exists()) uploads.mkdirs();

        File file = new File(uploads, filename);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return filename;
    }
}

