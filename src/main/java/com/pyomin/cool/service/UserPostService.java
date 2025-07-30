package com.pyomin.cool.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.PostLatestDto;
import com.pyomin.cool.dto.user.PostListDto;

public interface UserPostService {
    Page<PostListDto> getAllPosts(String category, Pageable page);

    PostDetailDto getPostBySlug(String slug);

    Post getPostOrThrow(Long postId);

    List<PostLatestDto> getLatestPosts();
}
