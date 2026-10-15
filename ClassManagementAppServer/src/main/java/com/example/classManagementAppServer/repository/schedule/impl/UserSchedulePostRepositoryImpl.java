package com.example.classManagementAppServer.repository.schedule.impl;

import com.example.classManagementAppServer.annotation.schedule.*;
import com.example.classManagementAppServer.dto.schedule.*;
import com.example.classManagementAppServer.repository.schedule.contract.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class UserSchedulePostRepositoryImpl implements UserSchedulePostRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @PostScheduleAnnotation
    public void postSchedule(Task task, int userID) {
        String sql = "INSERT INTO user_schedule(user_id, title, description, date, schedule_uuid) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql, userID, task.getTitle(), task.getDescription(), task.getLocalDate(), task.getUuid());
    }
}
