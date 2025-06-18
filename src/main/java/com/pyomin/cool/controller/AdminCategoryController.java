package com.pyomin.cool.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.admin.CategoryCreateDto;
import com.pyomin.cool.dto.admin.request.CategoryCreateRequest;
import com.pyomin.cool.dto.admin.response.CategoryListResponse;
import com.pyomin.cool.service.AdminCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService categoryService;

    @GetMapping
    public List<CategoryListResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public void createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        categoryService.createCategory(CategoryCreateDto.from(request));
    }
}
