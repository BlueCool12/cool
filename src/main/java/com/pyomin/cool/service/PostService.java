package com.pyomin.cool.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.pyomin.cool.dto.PostDetailDto;
import com.pyomin.cool.dto.PostLatestDto;
import com.pyomin.cool.dto.PostListDto;
import com.pyomin.cool.dto.SitemapDto;

public interface PostService {

    /**
     * 게시글 목록을 조회합니다.
     * 
     * <p>category가 비어 있으면 전체, 값이 있으면 해당 카테고리(slug)만 조회합니다.
     * 정렬은 전달된 {@link Pageable}의 sort를 따릅니다. (미지정 시 createdAt desc, id desc 권장)</p>
     * 
     * @param category 카테고리 slug (null/blank면 필터 미적용)
     * @param pageable 페이지/정렬 정보(0-based)
     * @return         게시글 목록 슬라이스 (다음 페이지 존재 여부 포함)     
     */
    Slice<PostListDto> getAllPosts(String category, Pageable pageable);

    PostDetailDto getPostBySlug(String slug);

    List<PostLatestDto> getLatestPosts();    

    List<SitemapDto<String>> getPostSitemap();

    /**
     * 키워드와 카테고리로 게시글을 검색합니다.
     *
     * <p>제목 또는 내용에 키워드가 포함된 게시글을 조회하며, 카테고리가 지정된 경우 해당 카테고리 내에서만 검색합니다.</p>
     *
     * @param keyword  검색 키워드 (null/blank인 경우 빈 결과 반환)
     * @param category 카테고리 slug (null/blank면 전체 검색)
     * @param pageable 페이지/정렬 정보(0-based)
     * @return         검색 결과 슬라이스 (다음 페이지 존재 여부 포함)
     */
    Slice<PostListDto> searchPosts(String keyword, String category, Pageable pageable);
}
