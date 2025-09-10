package com.pyomin.cool.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record SitemapDto<K>(K key, Instant lastModified) {

    public SitemapDto(K key, LocalDateTime lastModifiedLdt) {
        this(
                key,
                lastModifiedLdt == null
                        ? null
                        : lastModifiedLdt.atZone(ZoneId.of("Asia/Seoul")).toInstant());
    }
}