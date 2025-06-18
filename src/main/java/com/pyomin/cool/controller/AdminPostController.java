package com.pyomin.cool.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.PostDetailDto;
import com.pyomin.cool.dto.admin.PostUpdateDto;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;
import com.pyomin.cool.dto.admin.request.PostUpdateRequest;
import com.pyomin.cool.dto.admin.response.PostCreateResponse;
import com.pyomin.cool.dto.admin.response.PostDetailResponse;
import com.pyomin.cool.dto.admin.response.PostListResponse;
import com.pyomin.cool.service.AdminPostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    private final AdminPostService postService;

    @PostMapping
    public PostCreateResponse createPost(@Valid @RequestBody PostCreateRequest request) {
        PostCreateDto postCreateDto = PostCreateDto.from(request);
        Long id = postService.createPost(postCreateDto);
        return new PostCreateResponse(id);
    }

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
        System.out.println(request.isPublic());
        PostUpdateDto updateDto = PostUpdateDto.from(request);
        postService.updatePost(id, updateDto);
    }

}
