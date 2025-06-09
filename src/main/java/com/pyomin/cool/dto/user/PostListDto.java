package com.pyomin.cool.dto.user;

import java.util.List;

import com.pyomin.cool.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListDto {
    private final String title;
    private final String contentSummary;
    private final List<String> categories;
    private final String slug;
    private final String createdAt;

    public static PostListDto of(Post post, String summary) {
        return new PostListDto(
                post.getTitle(),
                summary,
                post.getCategories().stream()
                        .map(c -> c.getName())
                        .toList(),
                post.getSlug(),
                post.getCreatedAt().toString());
    }
}
