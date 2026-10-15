package com.example.classManagementAppServer.service.rest.schedule.test;

import lombok.*;
import org.springframework.http.*;
import org.springframework.scheduling.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class UserSchedulingTest {

    private final TaskScheduler taskScheduler;
    private final HashMap<String, ScheduledFuture> uuidToScheduledFutureMap = new HashMap<>();

    public ResponseEntity<String> startSchedule() {

        String uuid = UUID.randomUUID().toString();

        Runnable runnable = () -> {

            System.out.println("-------------------------------");
            System.out.println("schedule with uuid " + uuid);
            System.out.println("-------------------------------");
        };

        Instant instant = LocalDateTime.parse(LocalDate.now() + "T21:37:30").toInstant(ZoneOffset.ofHours(2));

        ScheduledFuture future = taskScheduler.schedule(runnable, instant);

        uuidToScheduledFutureMap.put(uuid, future);

        return ResponseEntity.ok(uuid);
    }

    public ResponseEntity<String> cancelSchedule(String uuid) {
        uuidToScheduledFutureMap.get(uuid).cancel(true);
        return ResponseEntity.ok("Thread " + uuid +" is terminated");
    }
}
