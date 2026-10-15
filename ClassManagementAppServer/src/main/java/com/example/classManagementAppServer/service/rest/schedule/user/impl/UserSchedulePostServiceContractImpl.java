package com.example.classManagementAppServer.service.rest.schedule.user.impl;

import com.example.classManagementAppServer.client.email.contract.*;
import com.example.classManagementAppServer.dto.schedule.*;
import com.example.classManagementAppServer.repository.schedule.contract.*;
import com.example.classManagementAppServer.service.rest.schedule.user.contract.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.cglib.core.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.scheduling.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Primary
public class UserSchedulePostServiceContractImpl implements UserSchedulePostServiceContract {

    private final UserSchedulePostRepositoryContract userSchedulePostRepositoryContract;
    private final UserScheduleDeleteRepositoryContract userScheduleDeleteRepositoryContract;

    private final UserScheduleReadServiceContract userScheduleReadServiceContract;
    private final UserScheduleDeleteServiceContract userScheduleDeleteServiceContract;

    private final UserSecurityContext userSecurityContext;

    private final MailSender mailSender;

    private final TaskScheduler taskScheduler;

//    @Override
//    public ResponseEntity<String> startSchedule(Task task) {
//
//        setUUID(task);
//        postSchedule(task);
//        HashMap<String, ScheduledFuture> uuidToScheduledFutureMap = userScheduleReadServiceContract.getUUIDToScheduledFutureMap();
//        Runnable runnable = runnableSchedule(task);
//        Instant instant = instant(task.getLocalDate());
//        ScheduledFuture future = scheduledFuture(runnable, instant);
//        putScheduleFutureToMap(uuidToScheduledFutureMap, task.getUuid(), future);
//        return ResponseEntity.ok("Task created successfully");
//    }

    @Override
    public ResponseEntity<String> startSchedule(Task task, String time) {
        setUUID(task);
        postSchedule(task);
        HashMap<String, ScheduledFuture> uuidToScheduledFutureMap = userScheduleReadServiceContract.getUUIDToScheduledFutureMap();
        Runnable runnable = runnableSchedule(task);
        Instant instant = instant(task.getLocalDate(), time);
        ScheduledFuture future = scheduledFuture(runnable, instant);
        putScheduleFutureToMap(uuidToScheduledFutureMap, task.getUuid(), future);
        return ResponseEntity.ok("Task created successfully");
    }

    @Override
    public void setUUID(Task task) {
        task.setUuid(UUID.randomUUID().toString());
    }

    @Override
    public void postSchedule(Task task) {
        userSchedulePostRepositoryContract.postSchedule(task, userSecurityContext.getUserID());
    }

    @Override
    public void putScheduleFutureToMap(HashMap<String, ScheduledFuture> uuidToScheduledFutureMap, String uuid, ScheduledFuture future) {
        uuidToScheduledFutureMap.put (uuid, future);
    }

    @Override
    public ScheduledFuture scheduledFuture(Runnable runnable, Instant instant) {
        return taskScheduler.schedule(runnable, instant);
    }

    @Override
    public Runnable runnableSchedule(Task task) {

        String to = userSecurityContext.getUsername();
        int userID = userSecurityContext.getUserID();

        return () ->
        {
            userScheduleDeleteServiceContract.deleteSchedule(task, userID);

//            mailSender.send(task.getDescription(), task.getTitle(), to);
            System.out.println("Description: "+task.getDescription()+"\nSubject: "+task.getTitle()+"\nTo: "+to);
        };
    }

    @Override
    public Instant instant(LocalDate localDate, String time) {
        return LocalDateTime.parse(localDate+"T"+time).toInstant(ZoneOffset.ofHours(2));
//        return Instant.parse(LocalDate.now()+"T21:23:00Z");
    }

//    @Override
//    public Instant instant(LocalDate localDate) {
//        return Instant.parse(localDate.toString()+"T00:00:00.00Z");
//    }
}
