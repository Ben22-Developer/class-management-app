package com.example.classManagementAppServer.config.async;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.*;

import java.util.concurrent.*;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor1 () {

        var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        threadPoolTaskExecutor.setTaskDecorator(new MyTaskDecorator());;
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }

    @Bean
    @Primary
    public TaskScheduler taskScheduler () {

        var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(100);
        threadPoolTaskScheduler.setTaskDecorator(new MyTaskDecorator());
        threadPoolTaskScheduler.initialize();

        return threadPoolTaskScheduler;
    }
}
