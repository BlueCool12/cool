package com.pyomin.cool.dto.admin;

import com.pyomin.cool.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

    public static CategoryDto of(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
