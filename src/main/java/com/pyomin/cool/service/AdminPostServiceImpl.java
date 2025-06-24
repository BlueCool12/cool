package com.pyomin.cool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.Category;
import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.dto.admin.PostDetailDto;
import com.pyomin.cool.dto.admin.PostListDto;
import com.pyomin.cool.dto.admin.PostUpdateDto;
import com.pyomin.cool.repository.CategoryRepository;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final AdminImageService adminImageService;

    @Value("${app.file-url-prefix}")
    private String fileUrlPrefix;

    @Override
    @Transactional
    public Long createPost(PostCreateDto postCreateDto) {
        String slug = generateSlug(postCreateDto.getTitle());
        Category category = categoryRepository.findById(postCreateDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Post post = postCreateDto.toEntity(slug, category);
        Post savedPost = postRepository.save(post);

        List<String> imagePaths = extractImagePaths(post.getContent());
        adminImageService.connectImagesToPost(savedPost.getId(), imagePaths);

        return savedPost.getId();
    }

    @Override
    public List<PostListDto> getAllPosts() {
        return postRepository.findAllPosts().stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public PostDetailDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostDetailDto.of(post);
    }

    @Override
    @Transactional
    public void updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        Category category = categoryRepository.findById(postUpdateDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        post.update(postUpdateDto.getTitle(), postUpdateDto.getContent(), category, postUpdateDto.isPublic());

        adminImageService.deleteMappingsByPostId(post.getId());
        List<String> imagePaths = extractImagePaths(post.getContent());
        adminImageService.connectImagesToPost(post.getId(), imagePaths);
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9가-힣\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

    private List<String> extractImagePaths(String content) {
        List<String> paths = new ArrayList<>();
        Pattern pattern = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String src = matcher.group(1);
            if (src.startsWith(fileUrlPrefix)) {
                String relativePath = src.substring(fileUrlPrefix.length());
                paths.add(relativePath);
            }
        }

        return paths;
    }

}
