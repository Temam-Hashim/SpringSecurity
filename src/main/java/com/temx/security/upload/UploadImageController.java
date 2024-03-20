package com.temx.security.upload;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
public class UploadImageController {

    private final UploadImageService service;

    @PostMapping("/local")
    public Map<String,String> uploadImage(@RequestParam("file") MultipartFile file) {

        String filePath = service.uploadImage(file);

        Map<String,String> data = new HashMap<>();
        data.put("Message","Image Uploaded Successfully");
        data.put("Code","200");
        data.put("Success","True");
        data.put("file",filePath);

        return data;
    }

    @PostMapping("/cloudinary")
    public Map<String,String> cloudinaryImageUpload(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = service.cloudinaryImageUpload(file);

            Map<String,String> data = new HashMap<>();
            data.put("Message","Image Uploaded Successfully");
            data.put("Code","200");
            data.put("Success","True");
            data.put("ImageUrl",imageUrl);
            return data;
        } catch (Exception e) {
            return (Map<String, String>) ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }
}
