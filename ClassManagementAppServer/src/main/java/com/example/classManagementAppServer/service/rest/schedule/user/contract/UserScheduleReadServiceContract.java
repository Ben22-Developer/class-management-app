package com.example.classManagementAppServer.service.rest.schedule.user.contract;

import java.util.*;
import java.util.concurrent.*;

public interface UserScheduleReadServiceContract {
    HashMap<String, ScheduledFuture> getUUIDToScheduledFutureMap();
}
