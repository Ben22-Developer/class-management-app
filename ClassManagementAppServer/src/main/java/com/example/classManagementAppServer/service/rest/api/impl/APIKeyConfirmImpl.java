package com.example.classManagementAppServer.service.rest.api.impl;

import com.example.classManagementAppServer.annotation.confirmApi.*;
import com.example.classManagementAppServer.async.contract.*;
import com.example.classManagementAppServer.client.email.contract.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.exception.sql.*;
import com.example.classManagementAppServer.repository.api.contract.delete.*;
import com.example.classManagementAppServer.repository.api.contract.read.*;
import com.example.classManagementAppServer.repository.user.contract.create.*;
import com.example.classManagementAppServer.repository.user.contract.read.*;
import com.example.classManagementAppServer.service.rest.api.contract.*;
import com.example.classManagementAppServer.service.security.matcher.contract.*;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Service("apiKeyConfirmImpl")
@RequiredArgsConstructor
public class APIKeyConfirmImpl implements APIKeyConfirmContract {

    @Resource(name="readAPIKeyRepositoryImpl")
    private ReadAPIKeyRepository readAPIKeyRepository;

    @Resource(name="readUserRepositoryImpl")
    private ReadUserRepositoryContract readUserRepository;

    @Resource(name="createUserRepositoryImpl")
    private CreateUserRepository createUserRepository;

    @Resource(name="simpleMailSender")
    private MailSender mailSender;

    @Resource(name="asyncImpl")
    private AsyncContract asyncContract;

    @Resource(name="deleteAPIKeyRepositoryImpl")
    private DeleteAPIKeyRepository deleteAPIKeyRepository;

    private final PasswordMatcher passwordMatcher;

    @Override
    @UserAPIKeyConfirmAnnotation
    public ResponseEntity<String> freeTierAPIKeyConfirm(UserBuilder userBuilder) // the arg is used in the Confirm API Key Aspect
    {
        var userBuilder1 = readAPIKeyRepository.getUnsavedUserByUsername(userBuilder);

        if (!validate(userBuilder, userBuilder1))
            throw new InvalidAPIKeyException("Invalid credentials provided!");

        interruptRemoveClaimedAPIKeyThread(userBuilder1);
        removeClaimedAPIKey(userBuilder1);
        return apiKeyUserRecord(userBuilder1);
    }

    @Override
    public void paidAPIKeyConfirm(UserBuilder userBuilder) {}

    @Override // sentUserBuilder -> sent builder to the controller ....
    public boolean validate(UserBuilder sentUserBuilder, UserBuilder retrievedUserBuilder) {
        return  (sentUserBuilder.getUsername().equals(retrievedUserBuilder.getUsername()) &&
        passwordMatcher.matches(sentUserBuilder.getPassword(), retrievedUserBuilder.getPassword()));
    }

    @Override
    public ResponseEntity<String> apiKeyUserRecord(UserBuilder userBuilder) {
        int tokenTypeID = readAPIKeyRepository.readTokenTypeID(userBuilder.getTokenCategory());
//        int tokenNumAmount = readAPIKeyRepository.readTokenNumAmount(tokenTypeID); this function can be deleted
        User user = userBuilder.build();
        createUserRepository.createUserAccount(user);
        user = userBuilder.setId(readUserRepository.readUserIDByUsername(user.username())).build();
        createUserRepository.createUserRoleAccount(user);
//        createUserRepository.createUserTokenAmountAccount(user, tokenNumAmount); this function can be deleted
        createUserRepository.createUserTokenTypeAccount(user,tokenTypeID);
        return ResponseEntity.ok("API Key saved successfully!");
    }

    @Override
    public void interruptRemoveClaimedAPIKeyThread(UserBuilder userBuilder) {
        asyncContract.interrupt(userBuilder.getUsername());
    }

    @Override
    public void removeClaimedAPIKey(UserBuilder userBuilder) {
        deleteAPIKeyRepository.deleteClaimedAPIKeyRecord(userBuilder.getPassword());
    }

    @Override
    public void mailPaidAPIKeyUser(UserBuilder userBuilder) {}
}
