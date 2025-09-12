package com.pyomin.cool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.pyomin.cool.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByIdAndPostId(Long id, Long postId);

    boolean existsByIdAndPassword(Long id, String password);

    List<Comment> findAllByPostIdOrderByCreatedAtAsc(@Param("postId") Long postId);
}
