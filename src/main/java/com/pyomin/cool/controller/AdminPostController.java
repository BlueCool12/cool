package com.pyomin.cool.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;
import com.pyomin.cool.dto.admin.response.PostCreateResponse;
import com.pyomin.cool.service.AdminPostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    private final AdminPostService postService;

    public AdminPostController(AdminPostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostCreateResponse createPost(@Valid @RequestBody PostCreateRequest request) {
        PostCreateDto postCreateDto = PostCreateDto.from(request);
        Long id = postService.createPost(postCreateDto);
        return new PostCreateResponse(id);
    }

}
