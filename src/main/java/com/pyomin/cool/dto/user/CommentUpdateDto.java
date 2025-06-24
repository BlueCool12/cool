package com.pyomin.cool.dto.user;

import com.pyomin.cool.dto.user.request.CommentUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateDto {

    private final Long id;
    private final String nickname;
    private final String password;
    private final String content;

    public static CommentUpdateDto from(Long id, CommentUpdateRequest request) {
        return new CommentUpdateDto(
                id,
                request.getNickname(),
                request.getPassword(),
                request.getContent());
    }
}
