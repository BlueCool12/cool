package com.pyomin.cool.dto.response;

import com.pyomin.cool.dto.CategoryDetailDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDetailResponse {
    private final String name;
    private final String slug;

    public static CategoryDetailResponse from(CategoryDetailDto dto) {
        return new CategoryDetailResponse(dto.getName(), dto.getSlug());
    }
}
