package com.pyomin.cool.dto.admin.response;

import com.pyomin.cool.dto.admin.CategoryDto;
import com.pyomin.cool.dto.admin.PostDetailDto;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(
        Long id,
        String title,
        String content,
        List<CategoryDto> categories,
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
                dto.getCategories(),
                dto.isPublic(),
                dto.isDeleted(),
                dto.getSlug(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }
}
