package com.pyomin.cool.dto.admin;

import com.pyomin.cool.dto.admin.request.CategoryCreateRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryCreateDto {
    private final String name;
    private final Long parentId;

    public static CategoryCreateDto from(CategoryCreateRequest request) {
        return new CategoryCreateDto(request.getName(), request.getParentId());
    }
}
