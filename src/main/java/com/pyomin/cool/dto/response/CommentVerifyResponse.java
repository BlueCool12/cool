package com.pyomin.cool.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CommentVerifyResponse {
    private boolean matched;
}
