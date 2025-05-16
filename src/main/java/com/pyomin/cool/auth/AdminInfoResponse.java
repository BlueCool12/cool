package com.pyomin.cool.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminInfoResponse {

    private String username;

    private List<String> roles;
}
