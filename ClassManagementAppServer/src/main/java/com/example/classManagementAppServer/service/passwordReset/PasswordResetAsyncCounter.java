package com.example.classManagementAppServer.service.passwordReset;

import com.example.classManagementAppServer.async.contract.AsyncContract;
import com.example.classManagementAppServer.repository.passwordReset.PasswordResetCodeRepositoryContract;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PasswordResetAsyncCounter implements AsyncContract
{

    private final HashMap<String, Thread> threadHashMap = new HashMap<>();

    private final PasswordResetCodeRepositoryContract passwordResetCodeRepository;

    @Override
    @Async
    public void startThread(String threadID, int minutes)
    {
        threadHashMap.put(threadID, Thread.currentThread());
        try
        {
            Thread.sleep(Duration.of(minutes, ChronoUnit.MINUTES));

            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException();

            passwordResetCodeRepository.deletePasswordResetCodeByUsername(threadID);

        }
        catch (InterruptedException e) {
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


