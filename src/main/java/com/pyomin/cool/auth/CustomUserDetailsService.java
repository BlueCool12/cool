package com.pyomin.cool.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals(adminUsername)) {
            throw new UsernameNotFoundException("존재하지 않는 관리자입니다.");
        }

        return User.builder()
                .username(adminUsername)
                .password(adminPassword)
                .roles("ADMIN")
                .build();
    }

}
