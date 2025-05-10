package com.pyomin.cool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);
}
