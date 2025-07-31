package com.pyomin.cool.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageViewLogDto {
    private String url;
    private String referrer;
    private String ipAddress;
    private String userAgent;
    private String sessionId;
    private String deviceType;

    public static PageViewLogDto of(
            String url,
            String referrer,
            String ipAddress,
            String userAgent,
            String sessionId) {
        return PageViewLogDto.builder()
                .url(url)
                .referrer(referrer != null ? referrer.trim() : "")
                .ipAddress(ipAddress != null ? ipAddress : "")
                .userAgent(userAgent != null ? userAgent : "")
                .sessionId(sessionId != null ? sessionId : "")
                .deviceType(resolveDeviceType(userAgent))
                .build();
    }

    private static String resolveDeviceType(String userAgent) {
        if (userAgent == null)
            return "unknown";
        String ua = userAgent.toLowerCase();
        if (ua.contains("tablet") || ua.contains("ipad"))
            return "tablet";
        if (ua.contains("mobile"))
            return "mobile";
        return "desktop";
    }
}
