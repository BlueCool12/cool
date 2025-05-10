package com.pyomin.cool.dto.admin;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;

import lombok.Getter;

@Getter
public class PostCreateDto {

    private final String title;
    private final String content;
    private final String category;
    private final boolean isPublic;

    private PostCreateDto(String title, String content, String category, boolean isPublic) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.isPublic = isPublic;
    }

    public static PostCreateDto from(PostCreateRequest request) {
        return new PostCreateDto(
                request.getTitle(),
                request.getContent(),
                request.getCategory(),
                request.getIsPublic());
    }

    public Post toEntity(String slug) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .isPublic(this.isPublic)
                .slug(slug)
                .build();
    }
}
