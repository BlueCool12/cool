package com.pyomin.cool.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchRequest {

    @Size(min = 2, message = "검색어는 2글자 이상이어야 합니다.")
    private String keyword;

    private String category;
}
