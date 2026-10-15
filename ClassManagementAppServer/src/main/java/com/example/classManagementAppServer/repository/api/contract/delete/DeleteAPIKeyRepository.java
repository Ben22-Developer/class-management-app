package com.example.classManagementAppServer.repository.api.contract.delete;

public interface DeleteAPIKeyRepository {
    boolean deleteClaimedAPIKeyRecord(String password);
}
