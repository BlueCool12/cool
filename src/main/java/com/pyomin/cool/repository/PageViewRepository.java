package com.pyomin.cool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.PageView;

public interface PageViewRepository extends JpaRepository<PageView, Long> {

}
