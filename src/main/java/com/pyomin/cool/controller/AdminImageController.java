package com.pyomin.cool.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pyomin.cool.dto.admin.response.ImageUploadResponse;
import com.pyomin.cool.service.AdminImageService;
import com.pyomin.cool.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/images")
public class AdminImageController {

    private final FileService fileService;

    private final AdminImageService adminImageService;

    @Value("${app.file-url-prefix}")
    private String fileUrlPrefix;

    @PostMapping
    public ImageUploadResponse uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            byte[] optimized = fileService.optimize(inputStream, originalFilename);
            String relativePath = fileService.save(optimized, originalFilename);
            String fileUrl = fileUrlPrefix + adminImageService.saveImage(relativePath);

            return new ImageUploadResponse(fileUrl);
        }
    }
}
