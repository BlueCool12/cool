package com.pyomin.cool.service;

import com.pyomin.cool.dto.admin.PostCreateDto;

public interface AdminPostService {
    Long createPost(PostCreateDto postCreateDto);

    String saveTemporaryImage(String path);
}
