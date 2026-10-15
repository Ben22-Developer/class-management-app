package com.example.classManagementAppServer.repository.user.contract.create;

import com.example.classManagementAppServer.dto.user.*;

public interface CreateUserRepository {
    void createUserAccount(User user);
    void createUserRoleAccount(User user);
    void createUserTokenTypeAccount(User user, int tokenTypeID);
    void createUserTokenAmountAccount(User user, int amount);
    void createPaidTokenValidity(User user, int subscriptionTime);
}
