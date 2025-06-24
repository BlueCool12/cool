package com.pyomin.cool.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentDeleteRequest {

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 4, max = 4, message = "비밀번호는 4자리 숫자여야 합니다.")
    @Pattern(regexp = "\\d{4}", message = "비밀번호는 숫자 4자리여야 합니다.")
    private String password;
}
