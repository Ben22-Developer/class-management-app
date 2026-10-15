package com.example.classManagementAppServer.repository.user.contract.read;

import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import org.springframework.security.core.*;

import java.util.*;

public interface ReadUserRepositoryContract {

    void readUserSecurityDetails (MyUserDetails myUserDetails);
    void readUserGrantedAuthorities(MyUserDetails myUserDetails, List<GrantedAuthority> authorities);
    int readUserIDByUsername (String username);
    String readUserTokenType(int id);
    int readUserTokenAmount(int id);
    String readUserRole(int id);
    List<UserBuilder> getAllUsername_id();
    List<UserBuilder> search(String searchString);
    UserBuilder getUsername_id(int id);
}
