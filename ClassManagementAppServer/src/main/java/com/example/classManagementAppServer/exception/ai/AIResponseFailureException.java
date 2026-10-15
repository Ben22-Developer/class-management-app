package com.example.classManagementAppServer.exception.ai;

public class AIResponseFailureException extends RuntimeException {
    public AIResponseFailureException(String message) {
        super(message);
    }
}
