package com.pyomin.cool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Comment;
import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.CommentCreateDto;
import com.pyomin.cool.dto.user.CommentListDto;
import com.pyomin.cool.dto.user.CommentUpdateDto;
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

        String password = dto.getPassword();
        if (password == null || !password.matches("\\d{4}")) {
            throw new IllegalArgumentException("비밀번호는 숫자 4자리여야 합니다.");
        }

        Comment comment = new Comment(post, parent, nickname, password, dto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public List<CommentListDto> getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

        Map<Long, CommentListDto> dtoMap = new HashMap<>();

        comments.forEach(comment -> {
            CommentListDto dto = CommentListDto.fromSingle(comment);
            dtoMap.put(comment.getId(), dto);
        });

        List<CommentListDto> result = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getParent() == null) {
                result.add(dtoMap.get(comment.getId()));
            } else {
                CommentListDto parentDto = dtoMap.get(comment.getParent().getId());
                if (parentDto != null) {
                    parentDto.getChildren().add(dtoMap.get(comment.getId()));
                }
            }
        }

        return result;
    }

    @Override
    @Transactional
    public void deleteComment(Long id, String password) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (!comment.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        comment.delete();
    }

    private String sanitizeNickname(String nickname) {
        return (nickname == null || nickname.trim().isEmpty()) ? "익명" : nickname.trim();
    }

    @Override
    public boolean verifyCommentPassword(Long commentId, String password) {
        return commentRepository.findById(commentId)
                .map(comment -> comment.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    @Transactional
    public void updateComment(CommentUpdateDto dto) {
        Comment comment = commentRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        comment.update(dto);
    }
}