package com.pyomin.cool.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pyomin.cool.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListDto {

    private final Long id;
    private final Long adminId;
    private final String nickname;
    private final String content;

    @JsonProperty("isDeleted")
    private final boolean deleted;

    private final OffsetDateTime createdAt;
    private final List<CommentListDto> children;

    public static CommentListDto fromSingle(Comment comment) {
        return new CommentListDto(
                comment.getId(),
                comment.getAdminId(),
                comment.getNickname(),
                comment.getContent(),
                comment.isDeleted(),
                comment.getCreatedAt(),
                new ArrayList<>());
    }

}
