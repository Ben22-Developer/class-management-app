package com.example.classManagementAppServer.dto.user;

import com.example.classManagementAppServer.dto.enums.*;
import lombok.*;

@Getter
public class UserBuilder {

    private int id;
    private String username;
    private String password;
    private TokenCategory tokenCategory;

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setUserPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setTokenCategory(TokenCategory tokenCategory) {
        this.tokenCategory = tokenCategory;
        return this;
    }

    public UserBuilder setTokenCategory(String tokenCategory) {
        this.tokenCategory = TokenCategory.valueOf(tokenCategory);
        return this;
    }

    public User build () {
        return new User(id, username, password, tokenCategory);
    }
}
