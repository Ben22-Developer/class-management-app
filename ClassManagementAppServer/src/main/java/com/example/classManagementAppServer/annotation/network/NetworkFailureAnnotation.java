package com.example.classManagementAppServer.annotation.network;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NetworkFailureAnnotation {
}
