package com.example.classManagementAppServer.exception.sql;

public class InvalidAPIKeyException extends RuntimeException {
    public InvalidAPIKeyException(String message) {
        super(message);
    }
}
