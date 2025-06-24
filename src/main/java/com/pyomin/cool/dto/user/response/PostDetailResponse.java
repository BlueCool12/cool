package com.pyomin.cool.dto.user.response;

import com.pyomin.cool.dto.user.PostDetailDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String category;
    private final String createdAt;

    public static PostDetailResponse from(PostDetailDto dto) {
        return new PostDetailResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getCategory(),
                dto.getCreatedAt());
    }
}
