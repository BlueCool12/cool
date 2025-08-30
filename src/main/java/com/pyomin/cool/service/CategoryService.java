package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.response.CategoryListResponse;

public interface CategoryService {

    List<CategoryListResponse> getAllCategories();

    Integer getCategoryIdBySlug(String slug);

}
