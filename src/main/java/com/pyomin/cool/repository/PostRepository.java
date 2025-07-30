package com.pyomin.cool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pyomin.cool.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllPosts();

    @EntityGraph(attributePaths = { "category" })
    @Query("""
                SELECT p FROM Post p
                WHERE p.isPublic = true AND p.isDeleted = false
                AND (:categoryId IS NULL OR p.category.id = :categoryId)
                ORDER BY p.createdAt DESC
            """)
    Page<Post> findVisiblePosts(@Param("categoryId") Long categoryId, Pageable pageable);

    Optional<Post> findBySlug(String slug);

    @Query("""
            SELECT p FROM Post p
            WHERE p.isDeleted = false AND p.isPublic = true
            ORDER BY p.createdAt DESC
            """)
    List<Post> findLatestPosts(Pageable pageable);
}
