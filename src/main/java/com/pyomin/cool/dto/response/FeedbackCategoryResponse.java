package com.pyomin.cool.dto.response;

import com.pyomin.cool.dto.FeedbackCategoryDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FeedbackCategoryResponse {
    private Integer id;
    private String name;

    public static FeedbackCategoryResponse from(FeedbackCategoryDto dto) {
        return FeedbackCategoryResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
