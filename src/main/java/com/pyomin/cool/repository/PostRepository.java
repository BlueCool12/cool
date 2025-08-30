package com.pyomin.cool.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.PostStatus;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = { "category" })
    @Query("""
                SELECT p FROM Post p
                WHERE p.status = :status
                    AND (:categoryId IS NULL OR p.category.id = :categoryId)
                ORDER BY p.createdAt DESC
            """)
    Page<Post> findVisiblePosts(@Param("status") PostStatus status,
            @Param("categoryId") Integer categoryId,
            Pageable pageable);

    @Query("""
            SELECT p FROM Post p
            WHERE p.status = :status
            AND (p.createdAt < :createdAt OR (p.createdAt = :createdAt AND p.id < :postId))
            ORDER BY p.createdAt DESC, p.id DESC
            """)
    List<Post> findPreviousPost(
            @Param("status") PostStatus status,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("postId") Long postId,
            Pageable pageable);

    @Query("""
            SELECT p FROM Post p
            WHERE p.status = :status
            AND (p.createdAt > :createdAt OR (p.createdAt = :createdAt AND p.id > :postId))
            ORDER BY p.createdAt ASC, p.id ASC
            """)
    List<Post> findNextPost(
            @Param("status") PostStatus status,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("postId") Long postId,
            Pageable pageable);

    @EntityGraph(attributePaths = { "category" })
    Optional<Post> findBySlug(String slug);

    @Query("""
            SELECT p FROM Post p
            WHERE p.status = :status
            ORDER BY p.createdAt DESC
            """)
    List<Post> findLatestPosts(@Param("status") PostStatus status, Pageable pageable);
}
