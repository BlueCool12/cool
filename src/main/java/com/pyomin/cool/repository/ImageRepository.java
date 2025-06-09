package com.pyomin.cool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
    Optional<Image> findByPath(String path);
}
