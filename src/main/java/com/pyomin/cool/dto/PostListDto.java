package com.pyomin.cool.dto;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListDto {
    private final String title;
    private final String contentSummary;
    private final String description;
    private final String category;
    private final String slug;
    private final String coverPath;
    private final String createdAt;
    private final String updatedAt;

    public static PostListDto of(Post post, String summary) {        
        return new PostListDto(
                post.getTitle(),
                summary,
                post.getDescription(),
                post.getCategory() != null ? post.getCategory().getName() : null,
                post.getSlug(),
                post.getCoverImage() != null ? "https://api.pyomin.com/files/" + post.getCoverImage().getPath() : null,
                post.getCreatedAt().toString(),
                post.getUpdatedAt().toString());
    }
}
