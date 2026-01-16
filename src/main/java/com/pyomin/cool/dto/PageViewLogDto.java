package com.pyomin.cool.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageViewLogDto {
    private String url;
    private String slug;
    private String referrer;
    private String ipAddress;
    private String userAgent;
    private String sessionId;
    private String deviceType;

    public static PageViewLogDto of(
            String url,
            String slug,
            String referrer,
            String ipAddress,
            String userAgent,
            String sessionId) {
        return PageViewLogDto.builder()
                .url(url)
                .slug(slug)
                .referrer(referrer != null ? referrer.trim() : "")
                .ipAddress(ipAddress != null ? ipAddress : "")
                .userAgent(userAgent != null ? userAgent : "")
                .sessionId(sessionId != null ? sessionId : "")
                .deviceType(resolveDeviceType(userAgent))
                .build();
    }

    private static String resolveDeviceType(String userAgent) {
        if (userAgent == null || userAgent.isBlank()) {
            return "unknown";
        }

        String ua = userAgent.toLowerCase();

        if (ua.contains("mediapartners-google")) {
            return "bot";
        }

        if (ua.contains("tablet") || ua.contains("ipad")) {
            return "tablet";
        }

        if (ua.contains("mobile")) {
            return "mobile";
        }

        return "desktop";
    }
}
