package com.pyomin.cool.dto.user;

import com.pyomin.cool.dto.user.request.CommentCreateRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateDto {

    private Long parentId;
    private Long postId;
    private String nickname;
    private String password;
    private String content;

    public static CommentCreateDto from(CommentCreateRequest request) {
        return new CommentCreateDto(
                request.getParentId(),
                request.getPostId(),
                request.getNickname(),
                request.getPassword(),
                request.getContent());
    }
}
