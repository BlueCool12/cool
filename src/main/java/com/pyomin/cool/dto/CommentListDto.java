package com.pyomin.cool.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pyomin.cool.domain.Comment;
import com.pyomin.cool.domain.CommentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListDto {

    private final Long id;
    private final Long adminId;
    private final String nickname;
    private final String content;
    private final CommentStatus status;
    private final OffsetDateTime createdAt;
    private final List<CommentListDto> children;

    public static CommentListDto fromSingle(Comment comment) {
        String nickname = comment.getStatus() == CommentStatus.DELETED ? "알 수 없음" : comment.getNickname();
        String content = comment.getStatus() == CommentStatus.DELETED ? "삭제된 댓글입니다." : comment.getContent();
        return new CommentListDto(
                comment.getId(),
                comment.getAdminId(),
                nickname,
                content,
                comment.getStatus(),
                comment.getCreatedAt(),
                new ArrayList<>());
    }
}
