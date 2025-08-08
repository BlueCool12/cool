package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.user.response.CategoryListResponse;

public interface UserCategoryService {

    List<CategoryListResponse> getAllCategories();

    Long getCategoryIdBySlug(String slug);

}
