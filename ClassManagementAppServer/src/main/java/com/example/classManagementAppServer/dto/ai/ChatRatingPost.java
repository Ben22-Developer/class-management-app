package com.example.classManagementAppServer.dto.ai;

import lombok.*;

@Data
public class ChatRatingPost {
    private final String userMessage;
    private final String botMessage;
    private final double rate;
}
