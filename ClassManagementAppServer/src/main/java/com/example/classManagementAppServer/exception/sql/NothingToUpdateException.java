package com.example.classManagementAppServer.exception.sql;

public class NothingToUpdateException extends RuntimeException {
    public NothingToUpdateException(String message) {
        super(message);
    }
}
