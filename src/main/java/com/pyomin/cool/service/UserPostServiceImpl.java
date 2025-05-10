package com.pyomin.cool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.dto.user.PostDetailDto;
import com.pyomin.cool.dto.user.PostListDto;
import com.pyomin.cool.dto.user.response.PostDetailResponse;
import com.pyomin.cool.dto.user.response.PostListResponse;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;

    @Override
    public List<PostListResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .filter(post -> post.isPublic() && !post.isDeleted())
                .map(post -> {
                    return PostListDto.of(post, summarize(post.getContent()));
                })
                .map(PostListResponse::from)
                .collect(Collectors.toList());
    }

    private String summarize(String content) {
        String plainText = content.replaceAll("<[^>]*>", "");
        return plainText.length() > 100 ? plainText.substring(0, 100) + "..." : plainText;
    }

    @Override
    public PostDetailResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        PostDetailDto dto = PostDetailDto.from(post);

        return PostDetailResponse.from(dto);
    }
}
