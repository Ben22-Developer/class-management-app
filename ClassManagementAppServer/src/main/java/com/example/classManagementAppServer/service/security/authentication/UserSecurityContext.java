package com.example.classManagementAppServer.service.security.authentication;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserSecurityContext {

    public int getUserID ()  {
        return getSecurityUserDetails().getUserBuilder().getId();
    }

    public String getUsername ()  {
        return getSecurityUserDetails().getUserBuilder().getUsername();
    }

    public String getUserRole ()  {

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities)
        {
            String authority = grantedAuthority.getAuthority();
            String roleSubString = authority.substring(0, 5);

            if (roleSubString.equals("ROLE_")) {
                return authority;
            }
        }
        throw new RuntimeException("User role not defined!");
    }

    public MyUserDetails getSecurityUserDetails () {
        return (MyUserDetails) getAuthenticationObject().getPrincipal();
    }

    public Authentication getAuthenticationObject () {
        return getSecurityContextHolder().getAuthentication();
    }

    public SecurityContext getSecurityContextHolder () {
        return SecurityContextHolder.getContext();
    }
}
