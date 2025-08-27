package com.pyomin.cool.dto.response;

import com.pyomin.cool.dto.PostListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponse {

    private final String title;
    private final String contentSummary;
    private final String description;
    private final String category;
    private final String slug;
    private final String createdAt;
    private final String updatedAt;

    public static PostListResponse from(PostListDto dto) {
        return new PostListResponse(
                dto.getTitle(),
                dto.getContentSummary(),
                dto.getDescription(),
                dto.getCategory(),
                dto.getSlug(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }
}
