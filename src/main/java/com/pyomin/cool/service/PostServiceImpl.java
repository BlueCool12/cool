package com.pyomin.cool.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Category;
import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.PostCategoryDto;
import com.pyomin.cool.dto.PostDetailDto;
import com.pyomin.cool.dto.PostLatestDto;
import com.pyomin.cool.dto.PostListDto;
import com.pyomin.cool.dto.PostSummaryDto;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryService categoryService;

    @Override
    @Transactional(readOnly = true)
    public Slice<PostListDto> getAllPosts(String category, Pageable pageable) {
        Slice<Post> slicedPosts = null;

        if (category == null) {
            slicedPosts = postRepository.findPublishedPosts(PostStatus.PUBLISHED, pageable);
        } else {
            Integer categoryId = categoryService.getCategoryIdBySlug(category);
            slicedPosts = postRepository.findPublishedPostsByCategory(PostStatus.PUBLISHED, categoryId, pageable);
        }

        return slicedPosts.map(post -> PostListDto.of(post, summarize(post.getContent())));
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

        Category category = post.getCategory();
        PostCategoryDto postCategoryDto = category != null
                ? PostCategoryDto.from(category)
                : new PostCategoryDto("카테고리 없음", "/");

        LocalDateTime createdAt = post.getCreatedAt();
        Long postId = post.getId();

        List<Post> prev = postRepository.findPreviousPost(PostStatus.PUBLISHED, createdAt, postId, limitOne);
        List<Post> next = postRepository.findNextPost(PostStatus.PUBLISHED, createdAt, postId, limitOne);

        PostSummaryDto prevDto = prev.isEmpty() ? null : PostSummaryDto.from(prev.get(0));
        PostSummaryDto nextDto = next.isEmpty() ? null : PostSummaryDto.from(next.get(0));

        return PostDetailDto.from(post, postCategoryDto, prevDto, nextDto);
    }

    @Override
    public Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostLatestDto> getLatestPosts() {
        List<Post> latestPosts = postRepository.findLatestPosts(PostStatus.PUBLISHED, PageRequest.of(0, 3));

        return latestPosts.stream()
                .map(PostLatestDto::from)
                .collect(Collectors.toList());
    }

}