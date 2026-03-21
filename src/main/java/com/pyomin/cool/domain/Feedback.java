package com.pyomin.cool.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private FeedbackCategory category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeedbackStatus status;

    @Column(name = "client_ip", length = 60)
    private String clientIp;

    @Column(name = "page_url")
    private String pageUrl;

    @Builder
    public Feedback(FeedbackCategory category, String content, String clientIp, String pageUrl) {
        this.category = category;
        this.content = content;
        this.status = FeedbackStatus.PENDING;
        this.clientIp = clientIp;
        this.pageUrl = pageUrl;
    }

    public void updateStatus(FeedbackStatus status) {
        this.status = status;
    }
}
