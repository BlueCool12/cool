package com.pyomin.cool.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.PostDetailDto;
import com.pyomin.cool.dto.PostLatestDto;
import com.pyomin.cool.dto.PostListDto;

public interface PostService {
    Slice<PostListDto> getAllPosts(String category, Pageable page);

    PostDetailDto getPostBySlug(String slug, Pageable limitOne);

    Post getPostOrThrow(Long postId);

    List<PostLatestDto> getLatestPosts();
}
