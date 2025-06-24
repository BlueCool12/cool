package com.pyomin.cool.dto.admin.response;

import com.pyomin.cool.dto.admin.CategoryDto;
import com.pyomin.cool.dto.admin.PostDetailDto;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long id,
        String title,
        String content,
        CategoryDto category,
        boolean isPublic,
        boolean isDeleted,
        String slug,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static PostDetailResponse from(PostDetailDto dto) {
        return new PostDetailResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getCategory(),
                dto.isPublic(),
                dto.isDeleted(),
                dto.getSlug(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }
}
