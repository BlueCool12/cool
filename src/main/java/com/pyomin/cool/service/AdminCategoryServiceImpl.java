package com.pyomin.cool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Category;
import com.pyomin.cool.dto.admin.CategoryCreateDto;
import com.pyomin.cool.dto.admin.response.CategoryListResponse;
import com.pyomin.cool.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void createCategory(CategoryCreateDto dto) {
        Category parent = null;

        if (dto.getParentId() != null) {
            parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));
        }

        Category category = Category.create(dto.getName(), parent);
        categoryRepository.save(category);
    }

    public List<CategoryListResponse> getAllCategories() {
        return categoryRepository.findAllCategories()
                .stream()
                .map(CategoryListResponse::from)
                .collect(Collectors.toList());
    }
}
