package com.example.classManagementAppServer.async.impl;

import com.example.classManagementAppServer.async.contract.*;
import com.example.classManagementAppServer.repository.api.contract.delete.*;
import jakarta.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Service
public class AsyncImpl implements AsyncContract {

    private final HashMap<String,Thread> threadHashMap = new HashMap<>();

    @Resource(name = "deleteAPIKeyRepositoryImpl")
    private DeleteAPIKeyRepository deleteAPIKeyRepository;
    @Override
    @Async
    public void startThread(String threadID, int minutes) {

        threadHashMap.put(threadID, Thread.currentThread());
        try
        {
            Thread.sleep(Duration.of(minutes, ChronoUnit.MINUTES));

            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException();

            deleteAPIKeyRepository.deleteClaimedAPIKeyRecord(threadID);

        } catch (InterruptedException e) {
            System.out.println("---------------------------------- is interrupted -----------------------------------");
        }
        threadHashMap.remove(threadID);
    }

    @Override
    public void interrupt (String threadID) {
        Thread thread = threadHashMap.get(threadID);
        thread.interrupt();
    }
}
