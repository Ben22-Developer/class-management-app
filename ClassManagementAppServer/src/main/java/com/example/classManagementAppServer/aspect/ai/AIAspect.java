package com.example.classManagementAppServer.aspect.ai;

import com.example.classManagementAppServer.exception.ai.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

@Service
@Aspect
public class AIAspect {

    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.ai.AIResponseFailureAnnotation)")
    public void aiResponseFailure () {
        throw new AIResponseFailureException("Unexpected error while generating response!");
    }


    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.ai.AIMaxTokenReachAnnotation)")
    public void aiMaxTokenReach () {
        throw new AIMaxTokenReachException("AI response failed possible reasons:\n1.Your have reached your max tokens so try again tomorrow.\n2.Your message contains emojis '😏', which aren't yet accepted.\n3.Our servers are down for a while so try again later.");
    }
}
