package com.pyomin.cool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.dto.user.response.CategoryListResponse;
import com.pyomin.cool.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCategoryServiceImpl implements UserCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryListResponse> getAllCategories() {
        return categoryRepository.findAllCategories()
                .stream()
                .map(CategoryListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCategoryIdBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + slug))
                .getId();
    }

}
