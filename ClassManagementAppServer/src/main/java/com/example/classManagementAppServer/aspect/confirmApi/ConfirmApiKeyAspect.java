package com.example.classManagementAppServer.aspect.confirmApi;

import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.exception.sql.*;
import com.example.classManagementAppServer.repository.user.contract.delete.*;
import jakarta.annotation.*;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

public class ConfirmApiKeyAspect {

    @Resource(name="deleteUserRepositoryImpl")
    private DeleteUserRepository deleteUserRepository;

    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.confirmApi.UserAPIKeyConfirmAnnotation)")
    public void userAPIKeyConfirmAnnotationAspect (JoinPoint joinPoint) {
        var userBuilder = (UserBuilder)  joinPoint.getArgs()[0];
        deleteUserRepository.deleteUserByUsername(userBuilder.getUsername());
        throw new InvalidSQLOperationException("An error occurred while processing this operation!\nPlease reclaim the api key later.");
    }


    @Before("@annotation(com.example.classManagementAppServer.annotation.confirmApi.ApiKeyValidateAnnotation)")
    public void validateAPIKey (JoinPoint joinPoint)
    {
        var userBuilder = (UserBuilder)  joinPoint.getArgs()[0];

    }
}
