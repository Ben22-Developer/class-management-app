package com.example.classManagementAppServer.service.security.authentication;

import com.example.classManagementAppServer.dto.user.*;
import lombok.*;
import org.jspecify.annotations.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;


@Data
public class MyUserDetails implements UserDetails {

    private UserBuilder userBuilder = new UserBuilder();
    private final List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return userBuilder.getPassword();
    }

    @Override
    public String getUsername() {
        return userBuilder.getUsername();
    }

    public void setUsername (String username) {
        userBuilder = userBuilder.setUsername(username);
    }

    public void setUserPassword (String password) {
        userBuilder = userBuilder.setUserPassword(password);
    }

    public void setUserID(int id) {
        userBuilder = userBuilder.setId(id);
    }
}
