package com.pyomin.cool.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.SitemapDto;
import com.pyomin.cool.dto.PostSummaryDto;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = { "category", "medias" })
    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED'")
    Slice<Post> findAllPublished(Pageable pageable);

    @EntityGraph(attributePaths = { "category", "medias" })
    @Query("""
            SELECT p
            FROM Post p
            JOIN p.category c
            WHERE p.status = 'PUBLISHED' AND c.slug = :slug
            """)
    Slice<Post> findByCategory(@Param("slug") String slug, Pageable pageable);

    @Query("""
            SELECT new com.pyomin.cool.dto.PostSummaryDto(p.slug, p.title)
            FROM Post p
            WHERE p.status = :status
            AND (p.createdAt < :createdAt OR (p.createdAt = :createdAt AND p.id < :postId))
            ORDER BY p.createdAt DESC, p.id DESC
            """)
    List<PostSummaryDto> findPreviousPost(
            @Param("status") PostStatus status,
            @Param("createdAt") OffsetDateTime createdAt,
            @Param("postId") Long postId,
            Pageable pageable);

    @Query("""
            SELECT new com.pyomin.cool.dto.PostSummaryDto(p.slug, p.title)
            FROM Post p
            WHERE p.status = :status
            AND (p.createdAt > :createdAt OR (p.createdAt = :createdAt AND p.id > :postId))
            ORDER BY p.createdAt ASC, p.id ASC
            """)
    List<PostSummaryDto> findNextPost(
            @Param("status") PostStatus status,
            @Param("createdAt") OffsetDateTime createdAt,
            @Param("postId") Long postId,
            Pageable pageable);

    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.category c
            LEFT JOIN FETCH p.medias m
            WHERE p.slug = :slug AND p.status = 'PUBLISHED'
            """)
    Optional<Post> findBySlug(@Param("slug") String slug);

    @Query("""
            SELECT p FROM Post p
            WHERE p.status = :status
            ORDER BY p.createdAt DESC, p.id DESC
            """)
    List<Post> findLatestPosts(@Param("status") PostStatus status, Pageable pageable);

    @Query("""
            SELECT new com.pyomin.cool.dto.SitemapDto(p.slug, COALESCE(p.updatedAt, p.createdAt))
            FROM Post p
            WHERE p.status = :status
            ORDER BY COALESCE(p.updatedAt, p.createdAt) DESC, p.id DESC
            """)
    List<SitemapDto<String>> findAllForSitemap(PostStatus status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.slug = :slug")
    void incrementViewCountBySlug(@Param("slug") String slug);
}
