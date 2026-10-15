package com.example.classManagementAppServer.aspect.sql;


import com.example.classManagementAppServer.exception.sql.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

@Service
@Aspect
public class SQLAspect {

    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.sql.UniqueConstraintAnnotation)")
    public void sqlUniqueConstraintFailure () {
        throw new SQLUniqueConstraintException("You have a similar pending operation currently!");
    }

    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.sql.InvalidUsernameAnnotation)")
    public void invalidUsername () {
        throw new InvalidUsernameException("Invalid username.\nPlease find another username to be set!");
    }

    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.sql.InvalidSQLOperationAnnotation)")
    public void timeoutSQLOperationAspect () {
        throw new InvalidSQLOperationException("Invalid operation!");
    }


}
