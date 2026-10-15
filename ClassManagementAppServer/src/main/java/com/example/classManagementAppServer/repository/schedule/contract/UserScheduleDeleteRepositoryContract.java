package com.example.classManagementAppServer.repository.schedule.contract;

import com.example.classManagementAppServer.dto.schedule.*;

public interface UserScheduleDeleteRepositoryContract {
    boolean deleteScheduleByUUID (String uuid);
    boolean deleteArchive (int userID, Task task);
}