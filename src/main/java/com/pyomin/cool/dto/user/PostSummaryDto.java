package com.pyomin.cool.dto.user;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSummaryDto {
    private String slug;
    private String title;

    public static PostSummaryDto from(Post post) {
        return new PostSummaryDto(post.getSlug(), post.getTitle());
    }
}
