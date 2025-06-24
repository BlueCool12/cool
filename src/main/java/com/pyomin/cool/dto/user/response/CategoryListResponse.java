package com.pyomin.cool.dto.user.response;

import java.util.List;

import com.pyomin.cool.domain.Category;

public record CategoryListResponse(
        String name,
        List<CategoryListResponse> children) {
    public static CategoryListResponse from(Category category) {
        return new CategoryListResponse(
                category.getName(),
                category.getChildren().stream()
                        .map(CategoryListResponse::from)
                        .toList());
    }
}