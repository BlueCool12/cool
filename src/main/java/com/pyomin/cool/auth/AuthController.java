package com.pyomin.cool.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyomin.cool.dto.admin.request.LoginRequest;
import com.pyomin.cool.dto.admin.response.LoginResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${cookie.secure}")
    private boolean isSecure;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {

        String token = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(isSecure)
                .sameSite(isSecure ? "None" : "Lax")
                .path("/")
                .maxAge(60 * 60)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
        return new LoginResponse(token);
    }

}
