package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.admin.CategoryCreateDto;
import com.pyomin.cool.dto.admin.response.CategoryListResponse;

public interface AdminCategoryService {

    void createCategory(CategoryCreateDto dto);

    List<CategoryListResponse> getAllCategories();

}
