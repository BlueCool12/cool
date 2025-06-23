package com.pyomin.cool.service;

import java.util.List;

import com.pyomin.cool.dto.user.CommentCreateDto;
import com.pyomin.cool.dto.user.CommentListDto;
import com.pyomin.cool.dto.user.CommentUpdateDto;

public interface UserCommentService {

    void createComment(CommentCreateDto dto);

    List<CommentListDto> getAllComments(Long postId);

    void deleteComment(Long id, String password);

    boolean verifyCommentPassword(Long commentId, String password);

    void updateComment(CommentUpdateDto dto);
}
