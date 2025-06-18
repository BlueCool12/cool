package com.pyomin.cool.dto.admin.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryCreateRequest {

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    private String name;

    private Long parentId;
}
