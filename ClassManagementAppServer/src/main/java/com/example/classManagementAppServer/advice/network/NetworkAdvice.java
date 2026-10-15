package com.example.classManagementAppServer.advice.network;

import com.example.classManagementAppServer.exception.network.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class NetworkAdvice {

    @ExceptionHandler(NetworkFailureException.class)
    public ResponseEntity<String> networkFailureExceptionAdvice
            (NetworkFailureException ex)
    {
        return ResponseEntity
                .status(HttpStatus.GATEWAY_TIMEOUT)
                .body(ex.getMessage());
    }
}
