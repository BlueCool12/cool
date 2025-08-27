package com.pyomin.cool.dto.response;

import com.pyomin.cool.dto.PostLatestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLatestResponse {

    private final Long id;
    private final String title;
    private final String slug;
    private final String createdAt;

    public static PostLatestResponse from(PostLatestDto post) {
        return new PostLatestResponse(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getCreatedAt());
    }
}
