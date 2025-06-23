package com.pyomin.cool.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentVerifyRequest {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
