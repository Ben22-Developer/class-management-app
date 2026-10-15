package com.example.classManagementAppServer.repository.schedule.contract;

import com.example.classManagementAppServer.dto.schedule.*;

public interface UserSchedulePostRepositoryContract {

    void postSchedule (Task task, int userID);
}
