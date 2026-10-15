package com.example.classManagementAppServer.service.rest.api.impl;

import com.example.classManagementAppServer.repository.user.contract.delete.*;
import com.example.classManagementAppServer.service.rest.api.contract.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class DeleteUserServiceImpl implements DeleteUserService {

    private final DeleteUserRepository deleteUserRepository;
    private final UserSecurityContext userSecurityContext;

    @Override
    public boolean deleteAccount() {
        var userName = userSecurityContext.getUsername();
        return deleteUserRepository.deleteUserByUsername(userName);
    }
}
