package com.pyomin.cool.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/sitemaps/download")
    public ResponseEntity<InputStreamResource> downloadSitemapZip() throws IOException {
        ByteArrayInputStream zipStream = fileService.generateSitemapZip(5000);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sitemaps.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(zipStream));
    }

    @GetMapping("/{yearMonth}/{fileName}")
    public ResponseEntity<InputStreamResource> serverFile(
            @PathVariable("yearMonth") String yearMonth,
            @PathVariable("fileName") String fileName) throws IOException {

        File file = new File(uploadDir + "/" + yearMonth + "/" + fileName);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStream inputStream = new FileInputStream(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
                .contentType(resolvMediaType(fileName))
                .body(new InputStreamResource(inputStream));
    }

    private MediaType resolvMediaType(String fileName) {
        String extension = fileName.toLowerCase();

        if (extension.endsWith(".jpg") || extension.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (extension.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (extension.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (extension.endsWith(".webp")) {
            return MediaType.valueOf("image/webp");
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}