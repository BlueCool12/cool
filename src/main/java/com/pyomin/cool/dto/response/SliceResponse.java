package com.pyomin.cool.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

public record SliceResponse<T>(
        List<T> content,
        int number,
        boolean hasNext) {
    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return new SliceResponse<>(
                slice.getContent(),
                slice.getNumber(),
                slice.hasNext());
    }
}
