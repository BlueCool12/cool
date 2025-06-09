package com.pyomin.cool.dto.user.response;

import java.util.List;

import com.pyomin.cool.dto.user.PostListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponse {

    private final String title;
    private final String contentSummary;
    private final List<String> categories;
    private final String slug;
    private final String createdAt;

    public static PostListResponse from(PostListDto dto) {
        return new PostListResponse(
                dto.getTitle(),
                dto.getContentSummary(),
                dto.getCategories(),
                dto.getSlug(),
                dto.getCreatedAt());
    }
}
