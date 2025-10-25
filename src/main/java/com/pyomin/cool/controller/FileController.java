package com.pyomin.cool.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/files")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/{year:\\d{4}}/{month:\\d{2}}/{fileName:.+}")
    public ResponseEntity<Resource> serverFile(
            @PathVariable("year") String year,
            @PathVariable("month") String month,
            @PathVariable("fileName") String fileName) throws IOException {

        Path base = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path path = base.resolve(Paths.get(year, month, fileName)).normalize();

        if (!path.startsWith(base)) {
            return ResponseEntity.status(403).build();
        }

        File file = path.toFile();
        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }

        String mime = Files.probeContentType(path);
        MediaType mediaType = (mime != null) ? MediaType.parseMediaType(mime) : resolvMediaType(fileName);

        String cd = ContentDisposition.inline()
                .filename(file.getName(), StandardCharsets.UTF_8)
                .build()
                .toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                .lastModified(file.lastModified())
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=2592000")
                .contentType(mediaType)
                .body(new FileSystemResource(file));
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