package com.example.classManagementAppServer.repository.schedule.contract;

import com.example.classManagementAppServer.dto.schedule.*;

import java.util.*;

public interface UserScheduleReadRepositoryContract {

    String getScheduleUUID (int userID, String scheduleTitle);
    List<Task> readAllScheduledTask(int userID);
    List<Task> readAllArchivedTask(int userID);
}
