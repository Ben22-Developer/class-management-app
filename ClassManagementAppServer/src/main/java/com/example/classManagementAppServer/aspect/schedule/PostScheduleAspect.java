package com.example.classManagementAppServer.aspect.schedule;

import com.example.classManagementAppServer.exception.sql.*;
import org.aspectj.lang.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

import java.sql.*;

@Service
@Aspect
public class PostScheduleAspect {

    @AfterThrowing(value="@annotation(com.example.classManagementAppServer.annotation.schedule.PostScheduleAnnotation)", throwing = "exception")
    public void postScheduleAfterThrowingAspect (Exception exception) throws Exception {

        if (exception instanceof SQLWarning)
            throw new SQLWarning("Data is too long!\nTitle is maximized to 200 characters\nDescription is maximized to  16 megabytes");

        if (exception instanceof DuplicateKeyException)
            throw new SQLIntegrityConstraintViolationException("The title you are trying to record is already recorded!");

        throw exception;
    }
}