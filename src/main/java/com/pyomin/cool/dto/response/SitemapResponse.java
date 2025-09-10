package com.pyomin.cool.dto.response;

import java.util.List;

public record SitemapResponse<T>(List<T> sitemap) {
    public SitemapResponse {
        sitemap = (sitemap == null) ? List.of() : List.copyOf(sitemap);
    }

    public static <T> SitemapResponse<T> from(List<T> sitemap) {
        return new SitemapResponse<>(sitemap);
    }
}