package com.pyomin.cool.dto;

import com.pyomin.cool.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCategoryDto {
    private final String name;
    private final String slug;

    public static PostCategoryDto from(Category category) {
        return new PostCategoryDto(category.getName(), category.getSlug());
    }
}
