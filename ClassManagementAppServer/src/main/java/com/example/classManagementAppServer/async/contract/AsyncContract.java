package com.example.classManagementAppServer.async.contract;

import java.util.*;

public interface AsyncContract {
    void startThread(String threadID, int minutes);
    void interrupt (String threadID);
}
