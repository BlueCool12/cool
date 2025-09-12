package com.pyomin.cool.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.SitemapDto;
import com.pyomin.cool.dto.PostSummaryDto;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = { "category" })
    Slice<Post> findByStatus(PostStatus status, Pageable pageable);

    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.category c
            WHERE p.status = :status AND c.slug = :slug
            """)
    Slice<Post> findByStatusAndCategory_Slug(PostStatus status, String slug, Pageable pageable);

    @Query("""
            SELECT new com.pyomin.cool.dto.PostSummaryDto(p.slug, p.title)
            FROM Post p
            WHERE p.status = :status
            AND (p.createdAt < :createdAt OR (p.createdAt = :createdAt AND p.id < :postId))
            ORDER BY p.createdAt DESC, p.id DESC
            """)
    List<PostSummaryDto> findPreviousPost(
            @Param("status") PostStatus status,
            @Param("createdAt") LocalDateTime createdAt,
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
            @Param("createdAt") LocalDateTime createdAt,
            @Param("postId") Long postId,
            Pageable pageable);

    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.category c
            WHERE p.slug = :slug AND p.status = :status
            """)
    Optional<Post> findBySlugAndStatus(@Param("slug") String slug, @Param("status") PostStatus status);

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
}
