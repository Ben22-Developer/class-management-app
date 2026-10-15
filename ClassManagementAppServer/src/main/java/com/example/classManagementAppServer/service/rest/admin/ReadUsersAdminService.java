package com.example.classManagementAppServer.service.rest.admin;

import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.user.contract.read.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReadUsersAdminService {

    private final ReadUserRepositoryContract readUserRepository;

    public List<UserBuilder> allUsername_id () {
        return readUserRepository.getAllUsername_id();
    }

    public List<UserBuilder> search (String search) {
        return readUserRepository.search(search);
    }

    public UserBuilder username_id (int id) {
        return readUserRepository.getUsername_id(id);
    }
}
