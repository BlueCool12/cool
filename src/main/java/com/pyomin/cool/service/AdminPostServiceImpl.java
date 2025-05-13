package com.pyomin.cool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Post;
import com.pyomin.cool.domain.PostImage;
import com.pyomin.cool.dto.admin.PostCreateDto;
import com.pyomin.cool.repository.PostImageRepository;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    @Value("${app.file-url-prefix}")
    private String fileUrlPrefix;

    @Override
    @Transactional
    public Long createPost(PostCreateDto postCreateDto) {
        String slug = generateSlug(postCreateDto.getTitle());
        Post post = postCreateDto.toEntity(slug);
        Post savedPost = postRepository.save(post);

        List<String> imagePaths = extractImagePaths(post.getContent());
        imagePaths.forEach(path -> connectImageToPost(savedPost.getId(), path));

        return savedPost.getId();
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9가-힣\\s]", "")
                .replaceAll("\\s+", "-");
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

    public void connectImageToPost(Long postId, String relativePath) {
        PostImage postImage = postImageRepository.findByPath(relativePath)
                .orElseThrow(() -> new RuntimeException("이미지 경로가 존재하지 않습니다: " + relativePath));
        postImage.setPost(postRepository.getReferenceById(postId));
        postImageRepository.save(postImage);
    }

    @Override
    @Transactional
    public String saveTemporaryImage(String path) {
        PostImage postImage = PostImage.of(path);
        postImageRepository.save(postImage);
        return path;
    }
}
