package com.example.classManagementAppServer.async.impl;

import com.example.classManagementAppServer.async.contract.*;
import com.example.classManagementAppServer.dto.ai.*;
import com.example.classManagementAppServer.repository.ai.contract.*;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.ai.chat.client.*;
import org.springframework.ai.chat.model.*;
import org.springframework.ai.chat.prompt.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRatingAsyncImpl implements ChatRatingAsyncContract {

    @Resource(name = "ratingChatClient")
    private final ChatClient chatClient;

    @org.springframework.beans.factory.annotation.Value("classpath:templates/spring_ai/system/chat-rating.st")
    private org.springframework.core.io.Resource resource;

    private final AIRepositoryContract aiRepository;

    @Override
    @SneakyThrows
    @Async
    public void chatRating (String userMessage, String chatBotMessage) {


        SystemPromptTemplate promptTemplate = SystemPromptTemplate
                .builder()
                .resource(resource)
                .variables(Map.of("userMessage", userMessage, "chatBotMessage", chatBotMessage))
                .build();

        System.out.println(promptTemplate.create());

        ResponseEntity<ChatResponse, ChatRatingPost> responseEntity =
                chatClient
                .prompt(promptTemplate.create())
                .call()
                .responseEntity(ChatRatingPost.class);

        System.out.println(responseEntity.response().getMetadata());
        System.out.println(responseEntity.getResponse().getResult().getOutput());
        System.out.println(responseEntity.getEntity());

        aiRepository.insertChatRating(responseEntity.getEntity());
    }
}
