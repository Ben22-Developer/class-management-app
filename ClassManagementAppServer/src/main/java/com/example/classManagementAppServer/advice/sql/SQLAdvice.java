package com.example.classManagementAppServer.advice.sql;

import com.example.classManagementAppServer.exception.sql.*;
import org.springframework.dao.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.*;


@RestControllerAdvice
public class SQLAdvice {

    @ExceptionHandler(SQLUniqueConstraintException.class)
    public ResponseEntity<String> sqlUniqueConstraintExceptionAdvice
            (SQLUniqueConstraintException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidSQLOperationException.class)
    public ResponseEntity<String> invalidSQLOperationExceptionAdvice
            (InvalidSQLOperationException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }


    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<String> invalidUsernameExceptionAdvice
            (InvalidUsernameException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(NothingToUpdateException.class)
    public ResponseEntity<String> nothingToUpdateExceptionAdvice
            (NothingToUpdateException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(DataTruncation.class)
    public ResponseEntity<String> dataTruncationAdvice
            (DataTruncation ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> dataTruncationAdvice
            (SQLIntegrityConstraintViolationException ex)
    {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> emptyResultDataAccessAdvice
            (EmptyResultDataAccessException ex)
    {
        return ResponseEntity
                .badRequest()
                .body("No results found.\nTry to refresh the interface for avoiding this error.");
    }
}
