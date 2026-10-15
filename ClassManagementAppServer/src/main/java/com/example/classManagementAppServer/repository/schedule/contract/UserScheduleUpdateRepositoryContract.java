package com.example.classManagementAppServer.repository.schedule.contract;

public interface UserScheduleUpdateRepositoryContract {
    boolean update (String sql, Object[] args);
}
