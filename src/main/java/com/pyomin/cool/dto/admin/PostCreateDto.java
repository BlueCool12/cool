package com.pyomin.cool.dto.admin;

import java.util.List;

import com.pyomin.cool.domain.Category;
import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.admin.request.PostCreateRequest;

import lombok.Getter;

@Getter
public class PostCreateDto {

    private final String title;
    private final String content;
    private final List<Long> categoryIds;
    private final boolean isPublic;

    private PostCreateDto(String title, String content, List<Long> categoryIds, boolean isPublic) {
        this.title = title;
        this.content = content;
        this.categoryIds = categoryIds;
        this.isPublic = isPublic;
    }

    public static PostCreateDto from(PostCreateRequest request) {
        return new PostCreateDto(
                request.getTitle(),
                request.getContent(),
                request.getCategoryIds(),
                request.getIsPublic());
    }

    public Post toEntity(String slug, List<Category> categories) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .categories(categories)
                .isPublic(this.isPublic)
                .slug(slug)
                .build();
    }
}
