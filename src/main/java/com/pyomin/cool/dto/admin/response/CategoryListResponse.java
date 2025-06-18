package com.pyomin.cool.dto.admin.response;

import java.util.List;
import java.util.stream.Collectors;

import com.pyomin.cool.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryListResponse {

    private Long id;
    private String name;
    private Long parentId;
    private List<CategoryChildResponse> children;

    public static CategoryListResponse from(Category parent) {
        return new CategoryListResponse(
                parent.getId(),
                parent.getName(),
                parent.getParent() != null ? parent.getParent().getId() : null,
                parent.getChildren().stream()
                        .map(CategoryChildResponse::from)
                        .collect(Collectors.toList()));
    }
}
