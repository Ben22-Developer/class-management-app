package com.example.classManagementAppServer.repository.user.contract.update;

import com.example.classManagementAppServer.dto.user.*;

public interface UserUpdateRepositoryContract {
    boolean updateUsername (Username username, String oldUsername);
    boolean updateUsername (String username, int id);
}
