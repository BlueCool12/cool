package com.pyomin.cool.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.PostDetailDto;
import com.pyomin.cool.dto.admin.PostUpdateDto;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;
import com.pyomin.cool.dto.admin.request.PostUpdateRequest;
import com.pyomin.cool.dto.admin.response.PostCreateResponse;
import com.pyomin.cool.dto.admin.response.PostDetailResponse;
import com.pyomin.cool.dto.admin.response.PostImageUploadResponse;
import com.pyomin.cool.dto.admin.response.PostListResponse;
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

    @GetMapping
    public List<PostListResponse> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(PostListResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDetailResponse getPost(@PathVariable("id") Long id) {
        PostDetailDto postDetailDto = postService.getPost(id);
        return PostDetailResponse.from(postDetailDto);
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostUpdateRequest request) {
        PostUpdateDto updateDto = PostUpdateDto.from(request);        
        postService.updatePost(id, updateDto);
    }

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
