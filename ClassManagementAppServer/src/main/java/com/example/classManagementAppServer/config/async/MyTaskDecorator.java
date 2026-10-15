package com.example.classManagementAppServer.config.async;

import org.springframework.core.task.*;

public class MyTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        return  () ->
        {
            System.out.println("-----------------------------------------------------------");
            System.out.println("------------------operation starts-------------------");
            System.out.println("-----------------------------------------------------------");
            runnable.run();
            System.out.println("-----------------------------------------------------------");
            System.out.println("------------------operation ends-------------------");
            System.out.println("-----------------------------------------------------------");
        };
    }
}
