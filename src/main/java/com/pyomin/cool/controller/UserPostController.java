package com.pyomin.cool.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.response.PostDetailResponse;
import com.pyomin.cool.dto.user.response.PostListResponse;
import com.pyomin.cool.service.UserPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/posts")
@RequiredArgsConstructor
public class UserPostController {

    private final UserPostService postService;

    @GetMapping
    public List<PostListResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{slug}")
    public PostDetailResponse getPostMySlug(@PathVariable("slug") String slug) {
        return postService.getPostBySlug(slug);
    }

}
