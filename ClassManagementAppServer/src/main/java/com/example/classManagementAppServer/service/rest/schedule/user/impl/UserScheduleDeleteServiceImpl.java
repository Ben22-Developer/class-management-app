package com.example.classManagementAppServer.service.rest.schedule.user.impl;

import com.example.classManagementAppServer.dto.schedule.*;
import com.example.classManagementAppServer.repository.schedule.contract.*;
import com.example.classManagementAppServer.service.rest.schedule.user.contract.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class UserScheduleDeleteServiceImpl implements UserScheduleDeleteServiceContract {

    private final UserScheduleDeleteRepositoryContract userScheduleDeleteRepositoryContract;
    private final UserScheduleReadRepositoryContract userScheduleReadRepositoryContract;

    private final UserScheduleReadServiceContract userScheduleReadServiceContract;

    @Override
    public ResponseEntity<?> deleteSchedule(List<Task> tasks, int userID) {

        ResponseEntity<?> response;

        for (Task task : tasks) {
            response = deleteSchedule(task, userID);
            if (!response.getStatusCode().is2xxSuccessful())
                return response;
        }
        return ResponseEntity.ok().body(tasks);
    }

    @Override
    public ResponseEntity<?> deleteSchedule(Task task, int userID) {

        String uuid = getTaskUUID(task.getTitle(), userID);
        boolean deleted = deleteTask(uuid);

        if (!deleted)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while deleting the task.\nPlease reload the window and try again later.");

        HashMap<String, ScheduledFuture> uuidToScheduledFutureMap = getUUIDToScheduledFutureMap();

        cancelScheduledFuture(uuidToScheduledFutureMap.get(uuid));
        removeScheduleFutureFromMap(uuidToScheduledFutureMap, uuid);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<?> deleteArchivedSchedule(List<Task> tasks, int userID) {

        for (Task task : tasks) {
            boolean b = userScheduleDeleteRepositoryContract.deleteArchive(userID, task);
            if (!b)
                return ResponseEntity.badRequest().body("There is nothing to delete");
        }
        return ResponseEntity.ok().body(tasks);
    }

    @Override
    public String getTaskUUID(String scheduleTitle, int userID) {
        return userScheduleReadRepositoryContract.getScheduleUUID(userID, scheduleTitle);
    }

    @Override
    public boolean deleteTask(String uuid) {
        return userScheduleDeleteRepositoryContract.deleteScheduleByUUID(uuid);
    }

    @Override
    public HashMap<String, ScheduledFuture> getUUIDToScheduledFutureMap() {
        return userScheduleReadServiceContract.getUUIDToScheduledFutureMap();
    }

    @Override
    public void cancelScheduledFuture(ScheduledFuture future) {
        if (future == null)
            return;
        future.cancel(true);
    }

    @Override
    public void removeScheduleFutureFromMap(HashMap<String, ScheduledFuture> uuidToScheduledFutureMap, String uuid) {
        uuidToScheduledFutureMap.remove(uuid);
    }
}
