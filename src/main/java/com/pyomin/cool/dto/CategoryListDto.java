package com.pyomin.cool.dto;

public record CategoryListDto(
                Integer id,
                String name,
                String slug,
                Integer parentId,
                Long postCount) {

}
