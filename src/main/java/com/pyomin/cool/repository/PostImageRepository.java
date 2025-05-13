package com.pyomin.cool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    Optional<PostImage> findByPath(String path);
}
