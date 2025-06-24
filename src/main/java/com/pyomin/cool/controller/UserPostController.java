package com.pyomin.cool.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private final UserPostService userPostService;

    @GetMapping
    public List<PostListResponse> getAllPosts(@RequestParam(required = false) String category) {
        return userPostService.getAllPosts(category).stream()
                .map(PostListResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{slug}")
    public PostDetailResponse getPostMySlug(@PathVariable("slug") String slug) {
        PostDetailDto postDetailDto = userPostService.getPostBySlug(slug);
        return PostDetailResponse.from(postDetailDto);
    }

}
