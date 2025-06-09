package com.pyomin.cool.dto.admin.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 255, message = "제목은 255자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotEmpty(message = "카테고리를 하나 이상 선택해야 합니다.")    
    private List<Long> categoryIds;

    @NotNull
    private Boolean isPublic;
}
