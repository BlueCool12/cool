package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.PostListDto;

public interface AdminPostService {

    List<PostListDto> getAllPosts();

    Long createPost(PostCreateDto postCreateDto);

    String saveTemporaryImage(String path);
}
