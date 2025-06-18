package com.pyomin.cool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.PostListDto;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;

    private final UserCategoryService categoryService;

    @Override
    public List<PostListDto> getAllPosts(String category) {
        Long categoryId = null;

        if (category != null) {
            categoryId = categoryService.getCategoryIdByName(category);
        }

        return postRepository.findVisiblePosts(categoryId).stream()
                .map(post -> PostListDto.of(post, summarize(post.getContent())))
                .collect(Collectors.toList());
    }

    private String summarize(String content) {
        String plainText = content.replaceAll("<[^>]*>", "");
        plainText = plainText.replaceAll("&[^;]+;", " ");
        return plainText.length() > 100 ? plainText.substring(0, 200) : plainText;
    }

    @Override
    public PostDetailDto getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return PostDetailDto.from(post);
    }
}
