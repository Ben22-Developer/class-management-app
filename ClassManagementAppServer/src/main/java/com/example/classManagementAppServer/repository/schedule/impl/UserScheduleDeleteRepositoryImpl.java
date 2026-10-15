package com.example.classManagementAppServer.repository.schedule.impl;

import com.example.classManagementAppServer.dto.schedule.*;
import com.example.classManagementAppServer.repository.schedule.contract.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class UserScheduleDeleteRepositoryImpl implements UserScheduleDeleteRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean deleteScheduleByUUID(String uuid) {
        String sql = "DELETE FROM user_schedule WHERE schedule_uuid=?";
        return jdbcTemplate.update(sql, uuid) > 0;
    }

    @Override
    public boolean deleteArchive(int userID, Task task) {
        String sql = "DELETE FROM archive_schedule WHERE user_id=? AND title=? AND date=?";
        return jdbcTemplate.update(sql, userID, task.getTitle(), task.getLocalDate()) > 0;
    }
}
