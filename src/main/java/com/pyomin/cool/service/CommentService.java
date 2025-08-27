package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.CommentCreateDto;
import com.pyomin.cool.dto.CommentListDto;
import com.pyomin.cool.dto.CommentUpdateDto;

public interface CommentService {

    void createComment(CommentCreateDto dto);

    List<CommentListDto> getAllComments(Long postId);

    void deleteComment(Long id, String password);

    boolean verifyCommentPassword(Long commentId, String password);

    void updateComment(CommentUpdateDto dto);
}
