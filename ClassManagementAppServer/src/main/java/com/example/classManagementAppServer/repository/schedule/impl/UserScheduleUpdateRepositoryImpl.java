package com.example.classManagementAppServer.repository.schedule.impl;

import com.example.classManagementAppServer.repository.schedule.contract.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class UserScheduleUpdateRepositoryImpl implements UserScheduleUpdateRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean update(String sql, Object[] args) {
        return jdbcTemplate.update(sql, args) > 0;
    }
}
