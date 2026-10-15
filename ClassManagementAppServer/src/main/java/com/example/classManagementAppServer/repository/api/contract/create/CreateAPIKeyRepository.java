package com.example.classManagementAppServer.repository.api.contract.create;

import com.example.classManagementAppServer.dto.user.*;

public interface CreateAPIKeyRepository {

    void createAPIKeyConfirmRecord (UserBuilder userBuilder);
}
