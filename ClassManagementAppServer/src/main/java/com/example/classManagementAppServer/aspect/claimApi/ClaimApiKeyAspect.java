package com.example.classManagementAppServer.aspect.claimApi;

import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.exception.sql.*;
import com.example.classManagementAppServer.repository.user.contract.read.*;
import jakarta.annotation.*;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

@Service
@Aspect
public class ClaimApiKeyAspect {

    @Resource(name="readUserRepositoryImpl")
    private ReadUserRepositoryContract readUserRepository;

    @Before("@annotation(com.example.classManagementAppServer.annotation.claimApi.ClaimApiKeyAnnotation)")
    public void beforeAPIKeyClaim (JoinPoint joinPoint) {

        UserBuilder userBuilder = (UserBuilder) joinPoint.getArgs()[0];

        try {
            readUserRepository.readUserIDByUsername(userBuilder.getUsername());
        } catch (Exception e) {
            return;
        }
        throw new SQLUniqueConstraintException("Can't create an API key for this already existing api!");
    }
}
