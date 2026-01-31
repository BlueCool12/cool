package com.pyomin.cool.dto;

import java.time.Instant;
import java.time.OffsetDateTime;

public record SitemapDto<K>(K key, Instant lastModified) {

    public SitemapDto(K key, OffsetDateTime lastModifiedLdt) {
        this(
                key,
                lastModifiedLdt == null
                        ? null
                        : lastModifiedLdt.toInstant());
    }
}