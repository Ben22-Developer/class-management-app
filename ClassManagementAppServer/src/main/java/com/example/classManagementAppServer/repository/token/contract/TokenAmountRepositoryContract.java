package com.example.classManagementAppServer.repository.token.contract;

public interface TokenAmountRepositoryContract {

    boolean deductTokenAmount(int tokens, int userID);

    int readTokenAmount(int userID);

    boolean resetTokenAmount(int userID);
}
