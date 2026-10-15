package com.example.classManagementAppServer.service.rest.ai;

import com.example.classManagementAppServer.annotation.ai.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ai.chat.client.*;
import org.springframework.ai.chat.memory.*;
import org.springframework.ai.chat.model.*;
import org.springframework.ai.chat.prompt.*;
import org.springframework.stereotype.*;

import com.example.classManagementAppServer.dto.ai.Prompt;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PromptHandlerService {

    private final UserSecurityContext userSecurityContext;

    private final ChatClient chatClient;

    @org.springframework.beans.factory.annotation.Value("classpath:templates/spring_ai/user/message.st")
    private org.springframework.core.io.Resource userResource;

    @org.springframework.beans.factory.annotation.Value("classpath:templates/spring_ai/system/message.st")
    private org.springframework.core.io.Resource systemResource;

    @AIResponseFailureAnnotation
    public ResponseEntity<?> prompt (Prompt prompt) {

        PromptTemplate promptTemplate = PromptTemplate // building the user prompt efficiently

                .builder()

                .resource(userResource) // Load the user prompt template from the configured resource.

                .variables(Map.of("prompt", prompt.getPrompt())) // Inject the user's query into the template placeholders.

                .build();

        var call = chatClient // the chat client to help in sending prompts

                .prompt() // start building the request.

                .system(systemResource) // Provide the system instructions that define the AI's behavior.

                .user(promptTemplate.create().getUserMessage().getText()) // Add the generated user prompt to the request.

                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userSecurityContext.getUsername()))  // Supply the conversation ID to any configured advisor that uses chat memory, if no one is found then it's ignored. The conversion id is 'eyo', u can visit the db to see what's going on

                .call(); // call the model and wait for response



        ChatResponse chatResponse = call.chatResponse(); // ChatResponse

        System.out.println("Prompt tokens --> "+chatResponse.getMetadata().getUsage().getPromptTokens());
        System.out.println("Completion tokens --> "+chatResponse.getMetadata().getUsage().getCompletionTokens());
        System.out.println("Total tokens --> "+chatResponse.getMetadata().getUsage().getTotalTokens());
//        System.out.println(chatResponse.getResult().getOutput().getText());



        return ResponseEntity.ok(chatResponse);
    }
}
