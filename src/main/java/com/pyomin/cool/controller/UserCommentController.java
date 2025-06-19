package com.pyomin.cool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.CommentCreateDto;
import com.pyomin.cool.dto.user.request.CommentCreateRequest;
import com.pyomin.cool.dto.user.response.CommentListResponse;
import com.pyomin.cool.service.UserCommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/comments")
public class UserCommentController {

    private final UserCommentService commentService;

    @PostMapping
    public void createComment(@RequestBody @Valid CommentCreateRequest request) {
        commentService.createComment(CommentCreateDto.from(request));
    }

    @GetMapping
    public CommentListResponse getAllComments(@RequestParam Long postId) {
        return CommentListResponse.from(commentService.getAllComments(postId));
    }
}
