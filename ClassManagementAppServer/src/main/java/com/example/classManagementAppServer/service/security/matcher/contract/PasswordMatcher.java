package com.example.classManagementAppServer.service.security.matcher.contract;

public interface PasswordMatcher {
    boolean matches (String rawPassword, String encryptedPassword);
}
