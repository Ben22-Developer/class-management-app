package com.example.classManagementAppServer.exception.sql;

public class InvalidSQLOperationException extends RuntimeException {
    public InvalidSQLOperationException(String message) {
        super(message);
    }
}
