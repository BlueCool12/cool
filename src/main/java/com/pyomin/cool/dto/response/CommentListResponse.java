package com.pyomin.cool.dto.response;

import java.util.List;

import com.pyomin.cool.dto.CommentListDto;

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
