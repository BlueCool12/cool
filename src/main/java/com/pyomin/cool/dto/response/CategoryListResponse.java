package com.pyomin.cool.dto.response;

import java.util.List;

import com.pyomin.cool.domain.Category;

public record CategoryListResponse(
        String name,
        String slug,
        List<CategoryListResponse> children) {
    public static CategoryListResponse from(Category category) {
        return new CategoryListResponse(
                category.getName(),
                category.getSlug(),
                category.getChildren().stream()
                        .map(CategoryListResponse::from)
                        .toList());
    }
}