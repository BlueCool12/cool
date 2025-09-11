package com.pyomin.cool.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.CategoryListDto;
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
        List<CategoryListDto> categories = categoryRepository.findAllForTree();

        Map<Integer, CategoryListResponse> nodes = new LinkedHashMap<>(categories.size());
        for (CategoryListDto category : categories) {
            nodes.put(
                    category.id(),
                    new CategoryListResponse(category.name(), category.slug(), new ArrayList<>()));
        }

        List<CategoryListResponse> roots = new ArrayList<>();
        for (CategoryListDto category : categories) {
            CategoryListResponse node = nodes.get(category.id());
            Integer parentId = category.parentId();

            if (parentId == null) {
                roots.add(node);
            } else {
                CategoryListResponse parentDto = nodes.get(parentId);
                if (parentDto != null) {
                    parentDto.children().add(node);
                }
            }
        }

        return roots;
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
