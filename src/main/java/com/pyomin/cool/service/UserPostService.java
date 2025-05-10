package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.user.response.PostDetailResponse;
import com.pyomin.cool.dto.user.response.PostListResponse;

public interface UserPostService {
    List<PostListResponse> getAllPosts();

    PostDetailResponse getPostBySlug(String slug);
}
