package com.pyomin.cool.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pyomin.cool.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListDto {

    private final Long id;
    private final String nickname;
    private final String content;

    @JsonProperty("isDeleted")
    private final boolean deleted;

    private final LocalDateTime createdAt;
    private final List<CommentListDto> children;

    public static CommentListDto from(Comment comment) {
        return new CommentListDto(
                comment.getId(),
                comment.getNickname(),
                comment.getContent(),
                comment.isDeleted(),
                comment.getCreatedAt(),
                comment.getChildren().stream()
                        .map(CommentListDto::from)
                        .collect(Collectors.toList()));
    }

    public static CommentListDto fromSingle(Comment comment) {
        return new CommentListDto(
                comment.getId(),
                comment.getNickname(),
                comment.getContent(),
                comment.isDeleted(),
                comment.getCreatedAt(),
                new ArrayList<>());
    }

}
