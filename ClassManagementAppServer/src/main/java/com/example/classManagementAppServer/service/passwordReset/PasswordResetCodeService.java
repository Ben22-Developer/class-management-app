package com.example.classManagementAppServer.service.passwordReset;

import com.example.classManagementAppServer.async.contract.AsyncContract;
import com.example.classManagementAppServer.client.email.contract.MailSender;
import com.example.classManagementAppServer.dto.passwordReset.PasswordResetCode;
import com.example.classManagementAppServer.exception.api.InvalidInputException;
import com.example.classManagementAppServer.repository.passwordReset.PasswordResetCodeRepositoryContract;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetCodeService
{
    private final PasswordResetCodeRepositoryContract passwordResetCodeRepository;

    @Resource(name="passwordResetAsyncCounter")
    private final AsyncContract passwordResetAsyncCounter;

    @jakarta.annotation.Resource(name="simpleMailSender")
    private MailSender mailSender;

    @Value("${spring.application.api-key-timeout}")
    private int apiKeyTimeout;

    @Transactional(rollbackFor = Exception.class)
    public boolean postPasswordResetCode (String username)
    {
        PasswordResetCode passwordResetCode = getPasswordResetCode(username);

        postPasswordResetCode(passwordResetCode);

        sendCodeMail(passwordResetCode);

        startCodeMailThreadCounter(passwordResetCode);

//        System.out.println(passwordResetCode);

        return true;
    }

    private PasswordResetCode getPasswordResetCode (String username)
    {
        PasswordResetCode passwordResetCode = new PasswordResetCode();


        String code = UUID.randomUUID().toString();

        passwordResetCode.setUsername(username);
        passwordResetCode.setLocalDateTime(LocalDateTime.now());
        passwordResetCode.setCode(code);

        return passwordResetCode;
    }

    private void postPasswordResetCode (PasswordResetCode passwordResetCode)
    {
        boolean posted = passwordResetCodeRepository.postPasswordResetCode(passwordResetCode);

        if (!posted)
            throw new RuntimeException("password reset code repository post failed!");
    }

    private void sendCodeMail (PasswordResetCode passwordResetCode)
    {
        mailSender.send("Password reset code: " + passwordResetCode.getCode() + "\nThis code is valid for not more than " + apiKeyTimeout + " minutes.",
                "Password Reset Code Mail",
                passwordResetCode.getUsername());
    }

    private void startCodeMailThreadCounter (PasswordResetCode passwordResetCode)
    {
        passwordResetAsyncCounter.startThread(passwordResetCode.getUsername(), apiKeyTimeout);
    }

    public boolean passwordResetCodeSubmit (PasswordResetCode userProvidedPasswordResetCode)
    {

        PasswordResetCode dbPasswordResetCode = passwordResetCodeRepository.getPasswordResetCodeByUsername(userProvidedPasswordResetCode.getUsername());

        passwordResetCodeValidityCheck(userProvidedPasswordResetCode, dbPasswordResetCode);

        cleanPasswordResetCodeUtilities (userProvidedPasswordResetCode.getUsername());

        return true;
    }

    private void passwordResetCodeValidityCheck (PasswordResetCode userProvidedPasswordResetCode, PasswordResetCode dbProvidedPasswordResetCode)
    {
        boolean codeIsValid = (userProvidedPasswordResetCode == null || dbProvidedPasswordResetCode == null) ? false : userProvidedPasswordResetCode.getCode().equals(dbProvidedPasswordResetCode.getCode());

        if (!codeIsValid)
            throw new InvalidInputException("Invalid code provided!");
    }

    private void cleanPasswordResetCodeUtilities (String username)
    {
        passwordResetCodeRepository.deletePasswordResetCodeByUsername(username);
        passwordResetAsyncCounter.interrupt(username);
    }
}
