package com.example.classManagementAppServer.service.rest.api.contract;

import com.example.classManagementAppServer.dto.user.*;
import org.springframework.http.*;

public interface APIKeyConfirmContract {

    ResponseEntity<String> freeTierAPIKeyConfirm(UserBuilder userBuilder);
    void paidAPIKeyConfirm(UserBuilder userBuilder);
    boolean validate (UserBuilder sentUserBuilder, UserBuilder retrievedUserBuilder);
    ResponseEntity<String> apiKeyUserRecord(UserBuilder userBuilder);
    void interruptRemoveClaimedAPIKeyThread (UserBuilder userBuilder);
    void removeClaimedAPIKey(UserBuilder userBuilder);
    void mailPaidAPIKeyUser (UserBuilder userBuilder);

//    ResponseEntity<String> deleteAPIKey (UserBuilder userBuilder);
}
