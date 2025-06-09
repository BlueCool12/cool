package com.pyomin.cool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
