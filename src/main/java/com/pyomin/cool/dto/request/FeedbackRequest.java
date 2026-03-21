package com.pyomin.cool.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedbackRequest {

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Integer categoryId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private String pageUrl;
}
