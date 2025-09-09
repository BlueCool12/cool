package com.pyomin.cool.dto.response;

import com.pyomin.cool.dto.PostDetailDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String content;
    private final CategoryDetailResponse category;
    private final String createdAt;

    private final PostSummaryResponse previousPost;
    private final PostSummaryResponse nextPost;

    public static PostDetailResponse from(PostDetailDto dto) {
        return new PostDetailResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getContent(),
                CategoryDetailResponse.from(dto.getCategoryDetailDto()),
                dto.getCreatedAt(),
                dto.getPreviousPost() != null ? PostSummaryResponse.from(dto.getPreviousPost()) : null,
                dto.getNextPost() != null ? PostSummaryResponse.from(dto.getNextPost()) : null);
    }
}
