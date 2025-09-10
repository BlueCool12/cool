package com.pyomin.cool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.SitemapDto;
import com.pyomin.cool.dto.response.CategoryListResponse;
import com.pyomin.cool.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

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
    public Integer getCategoryIdBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + slug))
                .getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SitemapDto<String>> getCategorySitemap() {
        return categoryRepository.findAllForSitemap(PostStatus.PUBLISHED);
    }
}
