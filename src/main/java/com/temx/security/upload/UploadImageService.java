package com.temx.security.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadImageService {
    // Directory where images will be stored
    private static final String UPLOAD_DIR = "uploads";

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    public String uploadImage(MultipartFile file) {
        // Generate a unique file name
        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "-" + file.getOriginalFilename());

        // Path to store the uploaded file
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();

        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(uploadPath);

            // Copy the file to the upload directory
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            // Return the file name (or path) to be stored in the database
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store image: " + ex.getMessage());
        }
    }


        public String cloudinaryImageUpload(MultipartFile file) throws IOException {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret
            ));

            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            return (String) uploadResult.get("url"); // Return the URL of the uploaded image
        }

}
