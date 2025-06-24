package com.pyomin.cool.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.response.CategoryListResponse;
import com.pyomin.cool.service.UserCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/categories")
public class UserCategoryController {

    private final UserCategoryService categoryService;

    @GetMapping
    public List<CategoryListResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
