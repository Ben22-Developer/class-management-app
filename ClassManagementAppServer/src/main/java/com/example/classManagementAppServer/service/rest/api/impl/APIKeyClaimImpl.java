package com.example.classManagementAppServer.service.rest.api.impl;


import com.example.classManagementAppServer.annotation.claimApi.*;
import com.example.classManagementAppServer.async.contract.*;
import com.example.classManagementAppServer.client.email.contract.*;
import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.api.contract.create.*;
import com.example.classManagementAppServer.service.rest.api.contract.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service("apiKeyClaimImpl")
public class APIKeyClaimImpl implements APIKeyClaimContract {

    @Resource(name="createAPIKeyRepositoryImpl")
    private CreateAPIKeyRepository createAPIKeyRepository;

    @Resource(name="asyncImpl")
    private AsyncContract asyncContract;

    @Resource(name="simpleMailSender")
    private MailSender mailSender;

    @Value("${spring.application.api-key-timeout}")
    private int apiKeyTimeout;

    @Value("${spring.application.admin.phone-number}")
    private String adminPhoneNumber;

    @Value("${spring.application.admin.gmail}")
    private String adminGmail;

//    @Resource(name="simpleMailSender")
//    private ThreadPoolExecutor threadPoolExecutor;

    private static String apiKeyClaimMsg = "You will receive a short email for your claimed API key.";

    @Override
    @ClaimApiKeyAnnotation
    @Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public ResponseEntity<String> freeTierApiKeyClaim(UserBuilder userBuilder) // this api builder is utilized in ClaimApiKey aspect bcz of ClaimApiKeyAnnotation
    {
        createApiKeyClaim(userBuilder, TokenCategory.FREE);
        return apiKeyClaim(userBuilder);
    }

    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public ResponseEntity<String> paidApiKeyClaim(UserBuilder userBuilder) {
        createApiKeyClaim(userBuilder, TokenCategory.PAID);
        return apiKeyClaim(userBuilder);
    }

    @Override
    public void createApiKeyClaim(UserBuilder userBuilder, TokenCategory tokenCategory) {
        String password = UUID.randomUUID().toString();
        userBuilder.setTokenCategory(tokenCategory);
        userBuilder.setUserPassword(password);
        createAPIKeyRepository.createAPIKeyConfirmRecord(userBuilder);
    }

    @Override
    public ResponseEntity<String> apiKeyClaim (UserBuilder userBuilder)
    {
        sendEmailForClaimedAPIKey(userBuilder);

        startRemoveClaimedAPIKeyThread(userBuilder);

//        System.out.println(userBuilder.getPassword());
        return ResponseEntity.ok(apiKeyClaimMsg);
//        return ResponseEntity.ok(apiKeyClaimMsg + "\nAPI key: " + userBuilder.getPassword() + "\n This password will be removed after testing!\nPlease remember that email functionality is still disabled!");
    }

    @Override
    public void sendEmailForClaimedAPIKey(UserBuilder userBuilder) {

        String txt =
                """
                    From class management app team.
                    Your API key: %s
                    %s
                """.formatted(userBuilder.getPassword(), getPaidAPIKeyForExtraMailTxt(userBuilder.getTokenCategory()));

        String subject = "API key Confirmation Email";

        mailSender.send(txt, subject, userBuilder.getUsername());
    }

    @Override
    public String getPaidAPIKeyForExtraMailTxt (TokenCategory tokenCategory) {

        return switch (tokenCategory)
        {
            case FREE ->
                    """
                    Confirm it in less than %d minutes
                    How to confirm it?
                    Open go to File > Settings
                    > Web Service > API (Tab) > Save
                    Click the 'Save' button
                    After you can enjoy our web services.
                    """.formatted(apiKeyTimeout);
            case PAID ->
                """
                    Your payment should be sent on this number %s
                    You will send an email to the admin on %s, confirming your payment.
                    Send your API key & telephone number in that email.
                    You must do this process before 00:00 AM if you go beyond the hour it'll be already timed out.
                    Then you have to wait patiently as you will be approved around 00:00 AM.
               """.formatted(adminPhoneNumber, adminGmail);
        };
    }

    @Override
    public void startRemoveClaimedAPIKeyThread (UserBuilder userBuilder) {
        asyncContract.startThread(userBuilder.getUsername(), apiKeyTimeout);
    }
}
