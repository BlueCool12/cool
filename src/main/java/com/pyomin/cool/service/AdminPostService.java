package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.PostDetailDto;
import com.pyomin.cool.dto.admin.PostListDto;
import com.pyomin.cool.dto.admin.PostUpdateDto;

public interface AdminPostService {

    List<PostListDto> getAllPosts();

    PostDetailDto getPost(Long id);

    Long createPost(PostCreateDto postCreateDto);

    void updatePost(Long id, PostUpdateDto postUpdateDto);

}
