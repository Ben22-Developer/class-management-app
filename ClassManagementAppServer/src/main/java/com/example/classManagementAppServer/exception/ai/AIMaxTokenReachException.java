package com.example.classManagementAppServer.exception.ai;

public class AIMaxTokenReachException extends RuntimeException {
    public AIMaxTokenReachException(String message) {
        super(message);
    }
}
