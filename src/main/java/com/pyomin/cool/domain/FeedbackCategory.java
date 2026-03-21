package com.pyomin.cool.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "feedback_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeedbackCategoryStatus status;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Builder
    public FeedbackCategory(String name, Integer sortOrder) {
        this.name = name;
        this.sortOrder = sortOrder != null ? sortOrder : 0;
        this.status = FeedbackCategoryStatus.ACTIVE;
    }
}
