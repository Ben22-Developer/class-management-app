package com.example.classManagementAppServer.aspect.network;

import com.example.classManagementAppServer.exception.network.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;

@Service
@Aspect
public class NetworkAspect {

    @AfterThrowing("@annotation(com.example.classManagementAppServer.annotation.network.NetworkFailureAnnotation)")
    public void networkFailure () {
        throw new NetworkFailureException("Network timeout\nPlease try again later!");
    }
}
