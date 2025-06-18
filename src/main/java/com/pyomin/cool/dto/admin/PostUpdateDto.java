package com.pyomin.cool.dto.admin;

import com.pyomin.cool.dto.admin.request.PostUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUpdateDto {

    private String title;
    private String content;
    private Long categoryId;
    private boolean isPublic;

    public static PostUpdateDto from(PostUpdateRequest request) {
        return new PostUpdateDto(
                request.getTitle(),
                request.getContent(),
                request.getCategoryId(),
                request.isPublic());
    }
}
