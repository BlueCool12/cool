package com.pyomin.cool.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.user.PageViewLogDto;
import com.pyomin.cool.dto.user.request.PageViewLogRequest;
import com.pyomin.cool.service.PageViewService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/page-view")
public class UserPageViewController {

    private final PageViewService pageViewService;

    @Value("${auth.frontend-key}")
    private String frontendKey;

    @PostMapping("/log")
    public void logPageView(HttpServletRequest request, @RequestBody PageViewLogRequest body) {
        String key = Optional.ofNullable(request.getHeader("X-Api-Access-Key")).orElse("");

        if (frontendKey == null || !frontendKey.trim().equals(key.trim())) {
            return;
        }

        if (body.getUrl() == null || body.getUrl().length() > 2000) {
            return;
        }

        String referrer = Optional.ofNullable(request.getHeader("X-Referrer")).orElse("");
        String ipAddress = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        String sessionId = Optional.ofNullable(request.getHeader("X-Session-Id"))
                .orElseGet(() -> getSessionIdFromCookie((request)));

        PageViewLogDto dto = PageViewLogDto.of(body.getUrl(), referrer, ipAddress, userAgent, sessionId);
        pageViewService.logPageView(dto);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return (ip != null) ? ip.split(",")[0] : request.getRemoteAddr();
    }

    private String getSessionIdFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies()) {
            if ("bluecool_sid".equals(cookie.getName()))
                return cookie.getValue();
        }
        return null;
    }
}
