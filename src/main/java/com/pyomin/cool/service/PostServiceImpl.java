package com.pyomin.cool.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.PostStatus;
import com.pyomin.cool.dto.CategoryDetailDto;
import com.pyomin.cool.dto.PostDetailDto;
import com.pyomin.cool.dto.PostLatestDto;
import com.pyomin.cool.dto.PostListDto;
import com.pyomin.cool.dto.SitemapDto;
import com.pyomin.cool.dto.PostSummaryDto;
import com.pyomin.cool.exception.ResourceNotFoundException;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private static final Pageable LIMIT_ONE = PageRequest.of(0, 1);

    @Override
    @Transactional(readOnly = true)
    public Slice<PostListDto> getAllPosts(String category, Pageable pageable) {
        final boolean hasCategory = StringUtils.hasText(category);

        Slice<Post> posts = hasCategory
                ? postRepository.findByCategory(category, pageable)
                : postRepository.findAllPublished(pageable);

        return posts.map(post -> PostListDto.of(post, summarize(post.getContent())));
    }

    private static String summarize(String content) {
        String plainText = content.replaceAll("<[^>]*>", "");
        plainText = plainText.replaceAll("&[^;]+;", " ");
        return plainText.length() > 300 ? plainText.substring(0, 300) : plainText;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailDto getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("게시글(slug: " + slug + ")을 찾을 수 없습니다."));

        PostSummaryDto prevDto = findPrev(post).orElse(null);
        PostSummaryDto nextDto = findNext(post).orElse(null);

        return PostDetailDto.from(post, CategoryDetailDto.from(post.getCategory()), prevDto, nextDto);
    }

    private Optional<PostSummaryDto> findPrev(Post post) {
        return postRepository.findPreviousPost(PostStatus.PUBLISHED, post.getCreatedAt(), post.getId(), LIMIT_ONE)
                .stream().findFirst();
    }

    private Optional<PostSummaryDto> findNext(Post post) {
        return postRepository.findNextPost(PostStatus.PUBLISHED, post.getCreatedAt(), post.getId(), LIMIT_ONE)
                .stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostLatestDto> getLatestPosts() {
        List<Post> latestPosts = postRepository.findLatestPosts(PostStatus.PUBLISHED, PageRequest.of(0, 3));

        return latestPosts.stream()
                .map(PostLatestDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SitemapDto<String>> getPostSitemap() {
        return postRepository.findAllForSitemap(PostStatus.PUBLISHED);
    }
}