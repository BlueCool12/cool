package com.pyomin.cool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Comment;
import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.CommentCreateDto;
import com.pyomin.cool.dto.CommentListDto;
import com.pyomin.cool.dto.CommentUpdateDto;
import com.pyomin.cool.exception.ResourceNotFoundException;
import com.pyomin.cool.repository.CommentRepository;
import com.pyomin.cool.repository.PostRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void createComment(CommentCreateDto dto) {
        Long postId = dto.getPostId();
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("게시글(id: " + postId + ")이 존재하지 않습니다.");
        }

        Long parentId = dto.getParentId();
        if (parentId != null && !commentRepository.existsByIdAndPostId(parentId, postId)) {
            throw new ResourceNotFoundException(
                    "부모 댓글(id: " + parentId + ")이 존재하지 않거나 해당 게시글(id: " + postId + ")의 댓글이 아닙니다.");
        }

        Post postRef = em.getReference(Post.class, postId);
        Comment parentRef = (parentId == null) ? null : em.getReference(Comment.class, parentId);

        Comment comment = new Comment(postRef, parentRef, dto.getNickname(), dto.getPassword(), dto.getContent());
        commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
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
        Comment comment = getCommentByIdOrThrow(id);

        if (!comment.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        comment.delete();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyCommentPassword(Long commentId, String password) {
        return commentRepository.existsByIdAndPassword(commentId, password);
    }

    @Override
    @Transactional
    public void updateComment(CommentUpdateDto dto) {
        Comment comment = getCommentByIdOrThrow(dto.getId());
        comment.update(dto);
    }

    private Comment getCommentByIdOrThrow(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("댓글(id: " + id + ")이 존재하지 않습니다."));
    }
}