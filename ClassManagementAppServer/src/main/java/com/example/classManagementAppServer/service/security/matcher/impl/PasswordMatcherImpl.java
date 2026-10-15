package com.example.classManagementAppServer.service.security.matcher.impl;

import com.example.classManagementAppServer.service.security.matcher.contract.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
@Primary
public class PasswordMatcherImpl implements PasswordMatcher {

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}
