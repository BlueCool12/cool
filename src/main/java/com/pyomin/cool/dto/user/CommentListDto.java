package com.pyomin.cool.dto.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.pyomin.cool.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListDto {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<CommentListDto> children;

    public static CommentListDto from(Comment comment) {
        return new CommentListDto(
                comment.getId(),
                comment.getNickname(),
                comment.isDeleted() ? "삭제된 댓글입니다." : comment.getContent(),
                comment.getCreatedAt(),
                comment.getChildren().stream()
                        .map(CommentListDto::from)
                        .collect(Collectors.toList()));
    }
}
