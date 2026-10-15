package com.example.classManagementAppServer.client.email.impl;

import com.example.classManagementAppServer.annotation.network.*;
import com.example.classManagementAppServer.client.email.contract.MailSender;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;

import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Primary
public class SimpleMailSender implements MailSender {

    private final JavaMailSender javaMailSender;

    private final UserSecurityContext userSecurityContext;

    @Value("${spring.application.name}")
    private String from;

    @Override
    @NetworkFailureAnnotation
    public Future<Boolean> send (String txt, String subject, String to) // before u change this params note that u're affecting Network Aspect
    {

        SimpleMailMessage simpleMailMessage = getSimpleMailMessage(txt, subject, to);
        simpleMailMessage.setFrom(from);

        javaMailSender.send(simpleMailMessage);

        return CompletableFuture.completedFuture(true);
    }

    private SimpleMailMessage getSimpleMailMessage (String txt, String subject, String to) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setText(txt);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);

        return simpleMailMessage;
    }
}
