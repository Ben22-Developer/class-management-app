package com.example.classManagementAppServer.service.rest.schedule.user.contract;

import com.example.classManagementAppServer.dto.schedule.*;
import org.springframework.http.*;

import java.util.*;
import java.util.concurrent.*;

public interface UserScheduleDeleteServiceContract {

    ResponseEntity<?> deleteSchedule (List<Task> tasks, int userID);

    ResponseEntity<?> deleteSchedule (Task task, int userID);

    ResponseEntity<?> deleteArchivedSchedule (List<Task> tasks, int userID);

    String getTaskUUID (String scheduleTitle, int userID);

    boolean deleteTask (String uuid);

    HashMap<String, ScheduledFuture> getUUIDToScheduledFutureMap();

    void cancelScheduledFuture (ScheduledFuture future);

    void removeScheduleFutureFromMap (HashMap<String, ScheduledFuture> uuidToScheduledFutureMap, String uuid);
}
