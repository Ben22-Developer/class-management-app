package com.example.classManagementAppServer.annotation.sql;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UniqueConstraintAnnotation {
}
