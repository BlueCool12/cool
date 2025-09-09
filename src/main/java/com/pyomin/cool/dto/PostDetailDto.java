package com.pyomin.cool.dto;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String content;
    private final CategoryDetailDto categoryDetailDto;
    private final String createdAt;

    private final PostSummaryDto previousPost;
    private final PostSummaryDto nextPost;

    public static PostDetailDto from(Post post, CategoryDetailDto categoryDetailDto, PostSummaryDto prev,
            PostSummaryDto next) {
        return new PostDetailDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getContent(),
                categoryDetailDto,
                post.getCreatedAt().toString(),
                prev,
                next);
    }
}
