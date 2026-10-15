package com.example.classManagementAppServer.client.email.contract;

import java.util.concurrent.*;

public interface MailSender {
    Future<Boolean> send(String txt, String subject, String to);
}
