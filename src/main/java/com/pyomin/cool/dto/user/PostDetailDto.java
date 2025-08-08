package com.pyomin.cool.dto.user;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String description;
    private final String category;
    private final String createdAt;

    private final PostSummaryDto previousPost;
    private final PostSummaryDto nextPost;

    public static PostDetailDto from(Post post, PostSummaryDto prev, PostSummaryDto next) {
        return new PostDetailDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDescription(),
                post.getCategory() != null ? post.getCategory().getName() : "카테고리 없음",
                post.getCreatedAt().toString(),
                prev,
                next);
    }
}
