package com.example.classManagementAppServer.exception.sql;

public class SQLUniqueConstraintException extends RuntimeException {
    public SQLUniqueConstraintException(String message) {
        super(message);
    }
}
