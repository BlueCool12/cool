package com.pyomin.cool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);
}
