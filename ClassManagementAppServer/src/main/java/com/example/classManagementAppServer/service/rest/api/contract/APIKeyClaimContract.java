package com.example.classManagementAppServer.service.rest.api.contract;

import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;
import org.springframework.http.*;

public interface APIKeyClaimContract {

    ResponseEntity<String> freeTierApiKeyClaim (UserBuilder userBuilder);
    ResponseEntity<String> paidApiKeyClaim (UserBuilder userBuilder);
    ResponseEntity<String> apiKeyClaim (UserBuilder userBuilder);
    void createApiKeyClaim(UserBuilder userBuilder, TokenCategory tokenCategory);
    void sendEmailForClaimedAPIKey(UserBuilder userBuilder); // password == API key
    String getPaidAPIKeyForExtraMailTxt (TokenCategory tokenCategory);
    void startRemoveClaimedAPIKeyThread(UserBuilder userBuilder);
}
