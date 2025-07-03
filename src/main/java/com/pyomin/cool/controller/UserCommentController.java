package com.pyomin.cool.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.CommentUpdateDto;
import com.pyomin.cool.dto.user.CommentCreateDto;
import com.pyomin.cool.dto.user.request.CommentCreateRequest;
import com.pyomin.cool.dto.user.request.CommentDeleteRequest;
import com.pyomin.cool.dto.user.request.CommentUpdateRequest;
import com.pyomin.cool.dto.user.request.CommentVerifyRequest;
import com.pyomin.cool.dto.user.response.CommentListResponse;
import com.pyomin.cool.dto.user.response.CommentVerifyResponse;
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
    public CommentListResponse getAllComments(@RequestParam("postId") Long postId) {
        return CommentListResponse.from(commentService.getAllComments(postId));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id, @RequestBody @Valid CommentDeleteRequest request) {
        commentService.deleteComment(id, request.getPassword());
    }

    @PostMapping("/{id}/verify")
    public CommentVerifyResponse verifyCommentPassword(@PathVariable("id") Long id,
            @RequestBody @Valid CommentVerifyRequest request) {
        boolean matched = commentService.verifyCommentPassword(id, request.getPassword());
        return CommentVerifyResponse.of(matched);
    }

    @PutMapping("/{id}")
    public void updateComment(@PathVariable("id") Long id, @RequestBody @Valid CommentUpdateRequest request) {
        commentService.updateComment(CommentUpdateDto.from(id, request));
    }
}
