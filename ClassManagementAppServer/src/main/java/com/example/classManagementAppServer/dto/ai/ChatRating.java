package com.example.classManagementAppServer.dto.ai;

import lombok.*;

@Data
public class ChatRating {
    private final int id;
    private final String userMessage;
    private final String botMessage;
    private final double rate;
    private final String localDateTime;
}
