package com.example.classManagementAppServer.service.rest.schedule.user.impl;

import com.example.classManagementAppServer.service.rest.schedule.user.contract.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class UserScheduleReadServiceImpl implements UserScheduleReadServiceContract {

    private HashMap<String, ScheduledFuture> uuidToScheduledFutureMap = new HashMap<>();

    @Override
    public HashMap<String, ScheduledFuture> getUUIDToScheduledFutureMap() {
        return uuidToScheduledFutureMap;
    }
}
