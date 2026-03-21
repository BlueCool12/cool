package com.pyomin.cool.dto;

import com.pyomin.cool.domain.FeedbackCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedbackCategoryDto {
    private Integer id;
    private String name;

    public static FeedbackCategoryDto from(FeedbackCategory category) {
        return FeedbackCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
