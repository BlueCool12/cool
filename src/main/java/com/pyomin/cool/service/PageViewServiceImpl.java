package com.pyomin.cool.service;

import org.springframework.stereotype.Service;

import com.pyomin.cool.domain.PageView;
import com.pyomin.cool.dto.PageViewLogDto;
import com.pyomin.cool.repository.PageViewRepository;
import com.pyomin.cool.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageViewServiceImpl implements PageViewService {

    private final PageViewRepository pageViewRepository;
    private final PostRepository postRepository;

    public void logPageView(PageViewLogDto dto) {

        if (dto.getUrl() == null || dto.getUrl().isBlank()) {
            return;
        }

        PageView pageView = PageView.builder()
                .url(dto.getUrl())
                .referrer(dto.getReferrer())
                .ipAddress(dto.getIpAddress())
                .userAgent(dto.getUserAgent())
                .sessionId(dto.getSessionId())
                .deviceType(dto.getDeviceType())
                .build();

        pageViewRepository.save(pageView);

        if (dto.getSlug() != null && !dto.getSlug().isBlank()) {
            postRepository.incrementViewCountBySlug(dto.getSlug());
        }
    }
}
