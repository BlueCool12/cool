package com.pyomin.cool.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.PostDetailDto;
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
        return postService.getAllPosts().stream()
                .map(PostListResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{slug}")
    public PostDetailResponse getPostMySlug(@PathVariable("slug") String slug) {
        PostDetailDto postDetailDto = postService.getPostBySlug(slug);
        return PostDetailResponse.from(postDetailDto);
    }

}
