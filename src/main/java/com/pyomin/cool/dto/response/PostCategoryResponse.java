package com.pyomin.cool.dto.response;

import com.pyomin.cool.dto.PostCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCategoryResponse {
    private final String name;
    private final String slug;

    public static PostCategoryResponse from(PostCategoryDto dto) {
        return new PostCategoryResponse(dto.getName(), dto.getSlug());
    }
}
