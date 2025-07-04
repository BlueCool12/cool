package com.pyomin.cool.dto.admin.response;

import com.pyomin.cool.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryChildResponse {

    private Long id;
    private String name;
    private Long parentId;

    public static CategoryChildResponse from(Category category) {
        return new CategoryChildResponse(
                category.getId(),
                category.getName(),
                category.getParent() != null ? category.getParent().getId() : null);
    }
}
