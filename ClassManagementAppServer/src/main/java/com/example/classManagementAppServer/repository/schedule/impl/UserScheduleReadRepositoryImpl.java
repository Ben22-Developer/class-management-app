package com.example.classManagementAppServer.repository.schedule.impl;

import com.example.classManagementAppServer.dto.schedule.*;
import com.example.classManagementAppServer.repository.schedule.contract.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserScheduleReadRepositoryImpl implements UserScheduleReadRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getScheduleUUID(int userID, String scheduleTitle) {

        String sql ="SELECT schedule_uuid FROM user_schedule WHERE user_id=? AND title=?";

        RowMapper<String> mapper = (resultSet,rowNbr) -> resultSet.getString(1);

        return jdbcTemplate.queryForObject(sql, mapper, userID, scheduleTitle);
    }

    @Override
    public List<Task> readAllScheduledTask(int userID) {
        String sql ="SELECT title, description, date FROM user_schedule WHERE user_id=?";
        return getScheduledOrArchivedTask(sql,userID);
    }

    @Override
    public List<Task> readAllArchivedTask(int userID) {
        String sql ="SELECT title, description, date FROM archive_schedule WHERE user_id=?";
        return getScheduledOrArchivedTask(sql,userID);
    }

    private List<Task> getScheduledOrArchivedTask (String sql, int userID) {

        List<Task> taskList = new ArrayList<>();

        RowMapper<Void> mapper = (resultSet,rowNbr) -> {

            if (resultSet.getObject(1).equals(null))
                return null;

            String title = resultSet.getString(1);
            String description = resultSet.getString(2);
            var date = resultSet.getObject(3, LocalDate.class);

            var task = new Task(date);
            task.setTitle(title);
            task.setDescription(description);

            taskList.add(task);

            return null;
        };
        jdbcTemplate.query(sql, mapper, userID);
        return taskList;
    }
}
