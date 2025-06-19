package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.PostListDto;

public interface UserPostService {
    List<PostListDto> getAllPosts(String category);

    PostDetailDto getPostBySlug(String slug);

    Post getPostOrThrow(Long postId);
}
