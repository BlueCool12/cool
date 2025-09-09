package com.pyomin.cool.dto;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLatestDto {

    private final Long id;
    private final String title;
    private final String slug;
    private final String createdAt;

    public static PostLatestDto from(Post post) {
        return new PostLatestDto(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getCreatedAt().toString());
    }
}
