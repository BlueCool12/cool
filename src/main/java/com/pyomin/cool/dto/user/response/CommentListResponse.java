package com.pyomin.cool.dto.user.response;

import java.util.List;

import com.pyomin.cool.dto.user.CommentListDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListResponse {

    private final List<CommentListDto> comments;

    public static CommentListResponse from(List<CommentListDto> comments) {
        return new CommentListResponse(comments);
    }
}
