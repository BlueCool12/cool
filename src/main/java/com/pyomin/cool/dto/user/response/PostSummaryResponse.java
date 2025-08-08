package com.pyomin.cool.dto.user.response;

import com.pyomin.cool.dto.user.PostSummaryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSummaryResponse {

    private final String slug;
    private final String title;

    public static PostSummaryResponse from(PostSummaryDto dto) {
        return new PostSummaryResponse(dto.getSlug(), dto.getTitle());
    }
}
