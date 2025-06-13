package com.pyomin.cool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pyomin.cool.dto.admin.response.CategoryListResponse;
import com.pyomin.cool.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryListResponse> getAllCategories() {
        return categoryRepository.findAllCategories()
                .stream()
                .map(CategoryListResponse::from)
                .collect(Collectors.toList());
    }
}
