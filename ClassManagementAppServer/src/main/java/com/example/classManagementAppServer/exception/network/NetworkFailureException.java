package com.example.classManagementAppServer.exception.network;

public class NetworkFailureException extends RuntimeException {
    public NetworkFailureException(String message) {
        super(message);
    }
}
