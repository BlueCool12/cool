package com.pyomin.cool.dto.admin;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListDto {

    private Long id;
    private String title;
    private String category;
    private boolean isPublic;
    private boolean isDeleted;
    private String slug;
    private String createdAt;
    private String updatedAt;

    public static PostListDto from(Post post) {
        return new PostListDto(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.isPublic(),
                post.isDeleted(),
                post.getSlug(),
                post.getCreatedAt().toString(),
                post.getUpdatedAt().toString());
    }
}
