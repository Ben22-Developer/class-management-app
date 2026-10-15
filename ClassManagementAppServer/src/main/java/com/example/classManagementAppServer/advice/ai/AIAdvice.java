package com.example.classManagementAppServer.advice.ai;

import com.example.classManagementAppServer.exception.ai.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class AIAdvice {

    @ExceptionHandler(AIMaxTokenReachException.class)
    public ResponseEntity<String> aiMaxTokenReachException (AIMaxTokenReachException exception)
    {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(AIResponseFailureException.class)
    public ResponseEntity<String> aiResponseFailureException (AIResponseFailureException exception)
    {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());

    }
}
