package com.pyomin.cool.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotNull(message = "postId는 필수입니다.")
    private Long postId;

    private Long parentId;

    @Size(max = 10, message = "닉네임은 최대 10자입니다.")
    private String nickname;

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(max = 250, message = "댓글은 최대 250자까지 입력할 수 있습니다.")
    private String content;

}
