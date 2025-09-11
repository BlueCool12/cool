package com.pyomin.cool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pyomin.cool.domain.Category;
import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.CategoryListDto;
import com.pyomin.cool.dto.SitemapDto;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
            SELECT new com.pyomin.cool.dto.CategoryListDto(c.id, c.name, c.slug, p.id)
            FROM Category c 
            LEFT JOIN c.parent p
            ORDER BY c.createdAt ASC
            """)
    List<CategoryListDto> findAllForTree();

    Optional<Category> findBySlug(String slug);

    @Query("""
            SELECT new com.pyomin.cool.dto.SitemapDto(c.slug, MAX(COALESCE(p.updatedAt, p.createdAt)))
            FROM Category c
            LEFT JOIN c.posts p ON p.status = :status
            WHERE c.parent IS NOT NULL
            GROUP BY c.slug
            """)
    List<SitemapDto<String>> findAllForSitemap(PostStatus status);
}
