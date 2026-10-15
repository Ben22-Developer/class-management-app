package com.example.classManagementAppServer.service.rest.schedule.user.contract;

import com.example.classManagementAppServer.dto.schedule.*;
import org.springframework.http.*;

import java.time.*;
import java.util.*;

public interface UserScheduleUpdateServiceContract {

    ResponseEntity<?> updateSchedule (Task currData, Task newData);
    ResponseEntity<?> post_DeleteSchedule (Task currData, Task newData);
    boolean isDateUpdated (LocalDate currDate, LocalDate newDate);
    String getUUID(Task currData);
    LinkedHashMap<String, String> clmToValueFormatter (Task task);
    String formatSQLQuery (LinkedHashMap<String, String> clmToValueCurrData, LinkedHashMap<String, String> clmToValueNewData);
    Object[] formatArgs (LinkedHashMap<String, String> clmToValueNewData, String uuid);
    boolean update(String sql, Object[] args);
}
