package com.pyomin.cool.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
    private boolean first;
    private boolean last;
    private boolean empty;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(page);
    }
}
