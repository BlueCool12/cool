package com.pyomin.cool.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.PostLatestDto;
import com.pyomin.cool.dto.user.PostListDto;
import com.pyomin.cool.dto.user.PostSummaryDto;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;

    private final UserCategoryService categoryService;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String LATEST_POSTS_KEY = "main:latest-posts";

    @Override
    public Page<PostListDto> getAllPosts(String category, Pageable pageable) {
        Long categoryId = null;

        if (category != null) {
            categoryId = categoryService.getCategoryIdBySlug(category);
        }

        Page<Post> page = postRepository.findVisiblePosts(categoryId, pageable);

        return page.map(post -> PostListDto.of(post, summarize(post.getContent())));
    }

    private String summarize(String content) {
        String plainText = content.replaceAll("<[^>]*>", "");
        plainText = plainText.replaceAll("&[^;]+;", " ");
        return plainText.length() > 250 ? plainText.substring(0, 250) : plainText;
    }

    @Override
    public PostDetailDto getPostBySlug(String slug, Pageable limitOne) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        LocalDateTime createdAt = post.getCreatedAt();
        Long postId = post.getId();

        List<Post> prev = postRepository.findPreviousPost(createdAt, postId, limitOne);
        List<Post> next = postRepository.findNextPost(createdAt, postId, limitOne);

        PostSummaryDto prevDto = prev.isEmpty() ? null : PostSummaryDto.from(prev.get(0));
        PostSummaryDto nextDto = next.isEmpty() ? null : PostSummaryDto.from(next.get(0));

        return PostDetailDto.from(post, prevDto, nextDto);
    }

    @Override
    public Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PostLatestDto> getLatestPosts() {
        Object cached = redisTemplate.opsForValue().get(LATEST_POSTS_KEY);

        if (cached != null) {
            return (List<PostLatestDto>) cached;
        }

        List<Post> latestPosts = postRepository.findLatestPosts(PageRequest.of(0, 3));

        List<PostLatestDto> dtoList = latestPosts.stream()
                .map(PostLatestDto::from)
                .collect(Collectors.toList());

        redisTemplate.opsForValue().set(LATEST_POSTS_KEY, dtoList, Duration.ofDays(1));

        return dtoList;
    }

}