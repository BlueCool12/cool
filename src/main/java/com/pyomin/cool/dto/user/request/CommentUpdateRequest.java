package com.pyomin.cool.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
