package com.pyomin.cool.repository;

import com.pyomin.cool.domain.FeedbackCategory;
import com.pyomin.cool.domain.FeedbackCategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategory, Integer> {
    List<FeedbackCategory> findAllByStatusOrderBySortOrderAsc(FeedbackCategoryStatus status);
}
