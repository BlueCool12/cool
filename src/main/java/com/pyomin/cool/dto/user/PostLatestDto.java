package com.pyomin.cool.dto.user;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
