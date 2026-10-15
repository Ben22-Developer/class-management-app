package com.example.classManagementAppServer.service.rest.schedule.user.impl;

import com.example.classManagementAppServer.dto.schedule.*;
import com.example.classManagementAppServer.repository.schedule.contract.*;
import com.example.classManagementAppServer.service.formatter.contract.*;
import com.example.classManagementAppServer.service.rest.schedule.user.contract.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserScheduleUpdateServiceImpl implements UserScheduleUpdateServiceContract {

     private final String tableName = "user_schedule";
     private final String whereClause = " schedule_uuid=?";

     private final UserSecurityContext userSecurityContext;

     private final UserSchedulePostServiceContract userSchedulePostServiceContract;
     private final UserScheduleDeleteServiceContract userScheduleDeleteServiceContract;

     private final UpdateSQLFormatterContract updateSQLFormatterContract;

     private final UserScheduleReadRepositoryContract userScheduleReadRepositoryContract;
     private final UserScheduleUpdateRepositoryContract userScheduleUpdateRepositoryContract;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> updateSchedule(Task currData, Task newData) {

        boolean dateUpdated = isDateUpdated(currData.getLocalDate(),newData.getLocalDate());

        if (dateUpdated)
            return post_DeleteSchedule(currData, newData);

        String uuid = getUUID(currData);

        LinkedHashMap<String, String> clmToValueCurrData = clmToValueFormatter(currData);
        LinkedHashMap<String, String> clmToValueNewData = clmToValueFormatter(newData);

        String sql = formatSQLQuery(clmToValueCurrData, clmToValueNewData);
        Object[] args = formatArgs(clmToValueNewData, uuid);
        boolean updated = update(sql, args);

        return updated ? ResponseEntity.ok("Task updated successfully.\n"+newData) : ResponseEntity.ok("Unexpected error occured while updating");
    }

    @Override
    public ResponseEntity<?> post_DeleteSchedule(Task currData, Task newData) {
        userScheduleDeleteServiceContract.deleteSchedule(currData, userSecurityContext.getUserID());
        userSchedulePostServiceContract.startSchedule(newData, "22:40:00");
        return ResponseEntity.ok("Task re-creation done successfully.\n"+newData);
    }

    @Override
    public boolean isDateUpdated(LocalDate currDate, LocalDate newDate) {
        return !currDate.isEqual(newDate);
    }

    @Override
    public String getUUID(Task currData) {
        return userScheduleReadRepositoryContract.getScheduleUUID(userSecurityContext.getUserID(), currData.getTitle());
    }

    @Override
    public LinkedHashMap<String, String> clmToValueFormatter(Task task) {

        LinkedHashMap<String, String> clmToValue = new LinkedHashMap<>();
        clmToValue.put("title", task.getTitle());
        clmToValue.put("description", task.getDescription());
        return clmToValue;
    }

    @Override
    public String formatSQLQuery(LinkedHashMap<String, String> clmToValueCurrData, LinkedHashMap<String, String> clmToValueNewData) {
        return updateSQLFormatterContract.formatUpdateSQLQuery(tableName, clmToValueCurrData, clmToValueNewData, whereClause);
    }

    @Override
    public Object[] formatArgs(LinkedHashMap<String, String> clmToValueNewData, String uuid) {

        Set<Map.Entry<String,String>> set = clmToValueNewData.entrySet();
        int size = set.size()+1; // the added 1 is for uuid
        Object[] objects = new Object[size];
        int i = 0;

        for (Map.Entry<String,String> s : set) {
            objects[i] = s.getValue();
            i+=1;
        }

        objects[i] = uuid;

        return objects;
    }

    @Override
    public boolean update(String sql, Object[] args) {
        return userScheduleUpdateRepositoryContract.update(sql, args);
    }
}
