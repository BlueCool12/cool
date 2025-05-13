package com.pyomin.cool.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;
import com.pyomin.cool.dto.admin.response.PostCreateResponse;
import com.pyomin.cool.dto.admin.response.PostImageUploadResponse;
import com.pyomin.cool.service.AdminPostService;
import com.pyomin.cool.service.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    private final AdminPostService postService;

    private final FileService fileService;

    @Value("${app.file-url-prefix}")
    private String fileUrlPrefix;

    @PostMapping
    public PostCreateResponse createPost(@Valid @RequestBody PostCreateRequest request) {
        PostCreateDto postCreateDto = PostCreateDto.from(request);
        Long id = postService.createPost(postCreateDto);
        return new PostCreateResponse(id);
    }

    @PostMapping("/images")
    public PostImageUploadResponse uploadImage(@RequestParam("upload") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            byte[] optimized = fileService.optimize(inputStream);
            String relativePath = fileService.save(optimized, originalFilename);
            String fileUrl = fileUrlPrefix + postService.saveTemporaryImage(relativePath);

            return new PostImageUploadResponse(fileUrl);
        }
    }
}
