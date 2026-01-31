package com.pyomin.cool.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.PostDetailDto;
import com.pyomin.cool.dto.SitemapDto;
import com.pyomin.cool.dto.response.SliceResponse;
import com.pyomin.cool.dto.response.PostDetailResponse;
import com.pyomin.cool.dto.response.PostLatestResponse;
import com.pyomin.cool.dto.response.PostListResponse;
import com.pyomin.cool.dto.response.SitemapResponse;
import com.pyomin.cool.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 목록을 조회합니다.
     * <p>category가 비어있으면 전체, 값이 있으면 해당 카테고리(slug) 글만 반환합니다.</p>
     * <p>정렬은 요청의 {@code sort} 파라미터를 따르며 미지정 시 {@code createdAt desc, id desc}가 기본값입니다.</p>
     * 
     * <pre>
     * 예) /posts?category=java&size=10&sort=createdAt,desc&sort=id,desc
     * </pre>
     * 
     * @param category 카테고리 slug (null/blank면 필터 미적용)
     * @param pageable 페이지/정렬 정보(0-base) 기본: size=10, sort=createdAt desc, id desc
     * @return         게시글 목록 슬라이스 응답 (다음 페이지 존재 여부 포함)
     */
    @GetMapping
    public SliceResponse<PostListResponse> list(
            @RequestParam(name = "category", required = false) String category,
            @PageableDefault(page = 0, size = 10, sort = { "createdAt",
                    "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Slice<PostListResponse> slice = postService.getAllPosts(category, pageable)
                .map(PostListResponse::from);

        return SliceResponse.from(slice);
    }

    @GetMapping("/{slug}")
    public PostDetailResponse getPostBySlug(@PathVariable("slug") String slug) {
        PostDetailDto postDetailDto = postService.getPostBySlug(slug);
        return PostDetailResponse.from(postDetailDto);
    }

    @GetMapping("/latest")
    public List<PostLatestResponse> getLatestPosts() {
        return postService.getLatestPosts().stream()
                .map(PostLatestResponse::from)
                .toList();
    }

    @GetMapping("/sitemap")
    public SitemapResponse<SitemapDto<String>> sitemap() {
        return SitemapResponse.from(postService.getPostSitemap());
    }
}
