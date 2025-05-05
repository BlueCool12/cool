package com.pyomin.cool.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pyomin.cool.dto.admin.request.LoginRequest;
import com.pyomin.cool.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public String login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return jwtProvider.generateToken(authentication);
    }
}
