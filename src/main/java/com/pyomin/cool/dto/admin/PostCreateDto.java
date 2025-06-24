package com.pyomin.cool.dto.admin;

import com.pyomin.cool.domain.Category;
import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;

import lombok.Getter;

@Getter
public class PostCreateDto {

    private final String title;
    private final String content;
    private final Long categoryId;
    private final boolean isPublic;

    private PostCreateDto(String title, String content, Long categoryId, boolean isPublic) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.isPublic = isPublic;
    }

    public static PostCreateDto from(PostCreateRequest request) {
        return new PostCreateDto(
                request.getTitle(),
                request.getContent(),
                request.getCategoryId(),
                request.getIsPublic());
    }

    public Post toEntity(String slug, Category category) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .category(category)
                .isPublic(this.isPublic)
                .slug(slug)
                .build();
    }
}
