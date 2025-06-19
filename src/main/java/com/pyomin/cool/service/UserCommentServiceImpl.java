package com.pyomin.cool.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Comment;
import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.CommentCreateDto;
import com.pyomin.cool.dto.user.CommentListDto;
import com.pyomin.cool.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommentServiceImpl implements UserCommentService {

    private final CommentRepository commentRepository;

    private final UserPostService postService;

    @Override
    @Transactional
    public void createComment(CommentCreateDto dto) {

        Post post = postService.getPostOrThrow(dto.getPostId());

        Comment parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        String nickname = sanitizeNickname(dto.getNickname());

        Comment comment = new Comment(post, parent, nickname, dto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public List<CommentListDto> getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);

        List<Comment> parent = comments.stream()
                .filter(comment -> comment.getParent() == null)
                .toList();

        return parent.stream()
                .map(CommentListDto::from)
                .toList();
    }

    private String sanitizeNickname(String nickname) {
        return (nickname == null || nickname.trim().isEmpty()) ? "익명" : nickname.trim();
    }
}