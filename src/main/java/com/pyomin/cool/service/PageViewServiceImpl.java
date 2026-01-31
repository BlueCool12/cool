package com.pyomin.cool.service;

import java.util.UUID;

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
                .slug(dto.getSlug())
                .referrer(dto.getReferrer())
                .ipAddress(dto.getIpAddress())
                .userAgent(dto.getUserAgent())
                .clientId(safeUuid(dto.getClientId()))
                .sessionId(safeUuid(dto.getSessionId()))
                .deviceType(dto.getDeviceType())
                .build();

        pageViewRepository.save(pageView);

        if (dto.getSlug() != null && !dto.getSlug().isBlank()) {
            postRepository.incrementViewCountBySlug(dto.getSlug());
        }
    }

    private UUID safeUuid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
