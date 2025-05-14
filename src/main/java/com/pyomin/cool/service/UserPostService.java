package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.PostListDto;

public interface UserPostService {
    List<PostListDto> getAllPosts();

    PostDetailDto getPostBySlug(String slug);
}
