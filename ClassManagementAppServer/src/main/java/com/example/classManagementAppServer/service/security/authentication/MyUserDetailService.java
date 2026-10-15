package com.example.classManagementAppServer.service.security.authentication;

import com.example.classManagementAppServer.repository.user.contract.read.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final ReadUserRepositoryContract readUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var authorities = new ArrayList<GrantedAuthority>();
        var myUserDetails = new MyUserDetails(authorities);

        myUserDetails.setUsername(username);
        readUserRepository.readUserSecurityDetails(myUserDetails);
        readUserRepository.readUserGrantedAuthorities(myUserDetails, authorities);

        return myUserDetails;
    }


}
