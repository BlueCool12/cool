package com.pyomin.cool.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.response.PageResponse;
import com.pyomin.cool.dto.user.response.PostDetailResponse;
import com.pyomin.cool.dto.user.response.PostLatestResponse;
import com.pyomin.cool.dto.user.response.PostListResponse;
import com.pyomin.cool.service.UserPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/posts")
@RequiredArgsConstructor
public class UserPostController {

    private final UserPostService userPostService;

    @GetMapping
    public PageResponse<PostListResponse> getAllPosts(
            @RequestParam(name = "category", required = false) String category,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<PostListResponse> page = userPostService.getAllPosts(category, pageable)
                .map(PostListResponse::from);

        return PageResponse.from(page);
    }

    @GetMapping("/{slug}")
    public PostDetailResponse getPostMySlug(@PathVariable("slug") String slug) {
        PostDetailDto postDetailDto = userPostService.getPostBySlug(slug);
        return PostDetailResponse.from(postDetailDto);
    }

    @GetMapping("/latest")
    public List<PostLatestResponse> getLatestPosts() {
        return userPostService.getLatestPosts().stream()
                .map(PostLatestResponse::from)
                .collect(Collectors.toList());
    }

}
