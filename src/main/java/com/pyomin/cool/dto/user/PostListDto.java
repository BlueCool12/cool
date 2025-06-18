package com.pyomin.cool.dto.user;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListDto {
    private final String title;
    private final String contentSummary;
    private final String category;
    private final String slug;
    private final String createdAt;

    public static PostListDto of(Post post, String summary) {
        return new PostListDto(
                post.getTitle(),
                summary,
                post.getCategory() != null ? post.getCategory().getName() : "카테고리 없음",
                post.getSlug(),
                post.getCreatedAt().toString());
    }
}
