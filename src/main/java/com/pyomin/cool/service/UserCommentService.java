package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.user.CommentCreateDto;
import com.pyomin.cool.dto.user.CommentListDto;

public interface UserCommentService {

    void createComment(CommentCreateDto dto);

    List<CommentListDto> getAllComments(Long postId);

    void deleteComment(Long id, String password);
}
