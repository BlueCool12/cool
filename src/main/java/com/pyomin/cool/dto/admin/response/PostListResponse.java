package com.pyomin.cool.dto.admin.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pyomin.cool.dto.admin.PostListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponse {

    private Long id;
    private String title;
    private String category;
    @JsonProperty("isPublic")
    private boolean isPublic;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private String slug;
    private String createdAt;
    private String updatedAt;

    public static PostListResponse from(PostListDto dto) {
        return new PostListResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getCategory(),
                dto.isPublic(),
                dto.isDeleted(),
                dto.getSlug(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

}
