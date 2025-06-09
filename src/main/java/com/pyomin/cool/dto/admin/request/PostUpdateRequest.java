package com.pyomin.cool.dto.admin.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private List<Long> categoryIds;

    @JsonProperty("isPublic")
    private boolean isPublic;
}
