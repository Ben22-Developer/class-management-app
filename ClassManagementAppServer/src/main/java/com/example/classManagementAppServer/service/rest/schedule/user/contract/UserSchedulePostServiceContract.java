package com.example.classManagementAppServer.service.rest.schedule.user.contract;

import com.example.classManagementAppServer.dto.schedule.*;
import org.springframework.http.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public interface UserSchedulePostServiceContract {

    ResponseEntity<String> startSchedule(Task task, String time); // for testing purpose, time ex '15:30:00'

    // ResponseEntity<String> startSchedule(Task task);

    void setUUID (Task task);

    void postSchedule (Task task);

    void putScheduleFutureToMap (HashMap<String, ScheduledFuture> uuidToScheduledFutureMap, String uuid, ScheduledFuture future);

    ScheduledFuture scheduledFuture (Runnable runnable, Instant instant);

    Runnable runnableSchedule (Task task);

    Instant instant (LocalDate localDate, String time); // for testing purpose, time ex '15:30:00'

    //Instant instant (LocalDate localDate);
}
