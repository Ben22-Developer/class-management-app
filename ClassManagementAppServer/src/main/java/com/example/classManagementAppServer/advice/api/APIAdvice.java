package com.example.classManagementAppServer.advice.api;

import com.example.classManagementAppServer.exception.api.InvalidInputException;
import com.example.classManagementAppServer.exception.sql.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class APIAdvice {

    @ExceptionHandler(InvalidAPIKeyException.class)
    public ResponseEntity<String> sqlUniqueConstraintExceptionAdvice
            (InvalidAPIKeyException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> invalidInputExceptionAdvice
            (InvalidInputException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
