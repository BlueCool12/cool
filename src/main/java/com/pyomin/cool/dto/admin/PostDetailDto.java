package com.pyomin.cool.dto.admin;

import java.time.LocalDateTime;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailDto {

    private Long id;
    private String title;
    private String content;
    private CategoryDto category;
    private boolean isPublic;
    private boolean isDeleted;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostDetailDto of(Post post) {
        return new PostDetailDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory() != null ? CategoryDto.of(post.getCategory()) : null,
                post.isPublic(),
                post.isDeleted(),
                post.getSlug(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
