package com.pyomin.cool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pyomin.cool.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllPosts();

    @Query("SELECT p FROM Post p WHERE p.isPublic = true AND p.isDeleted = false ORDER BY p.createdAt DESC")
    List<Post> findVisiblePosts();

    Optional<Post> findBySlug(String slug);
}
