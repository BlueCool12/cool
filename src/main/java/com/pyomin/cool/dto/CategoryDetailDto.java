package com.pyomin.cool.dto;

import com.pyomin.cool.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDetailDto {
    private final String name;
    private final String slug;

    public static CategoryDetailDto from(Category category) {
        return new CategoryDetailDto(category.getName(), category.getSlug());
    }
}
