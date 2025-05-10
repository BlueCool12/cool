package com.pyomin.cool.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.repository.PostRepository;

@Service
public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;

    public AdminPostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Long createPost(PostCreateDto postCreateDto) {
        String slug = generateSlug(postCreateDto.getTitle());
        Post post = postCreateDto.toEntity(slug);
        return postRepository.save(post).getId();
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9가-힣\\s]", "")
                .replaceAll("\\s+", "-");
    }
}
