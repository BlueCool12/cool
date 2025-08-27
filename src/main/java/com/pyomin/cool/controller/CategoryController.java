package com.pyomin.cool.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.response.CategoryListResponse;
import com.pyomin.cool.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryListResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
