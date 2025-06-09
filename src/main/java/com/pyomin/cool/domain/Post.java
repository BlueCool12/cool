package com.pyomin.cool.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "post_category", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Column(name = "category", insertable = false, updatable = false)
    private String legacyCategory;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPublic;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(nullable = false, length = 255, unique = true)
    private String slug;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        if (!category.getPosts().contains(this)) {
            category.getPosts().add(this);
        }
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getPosts().remove(this);
    }

    public void update(String title, String content, List<Category> newCategories, boolean isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;

        for (Category category : new ArrayList<>(this.categories)) {
            removeCategory(category);
        }

        for (Category category : newCategories) {
            addCategory(category);
        }
    }

}
