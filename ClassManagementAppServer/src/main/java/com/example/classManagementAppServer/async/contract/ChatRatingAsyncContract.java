package com.example.classManagementAppServer.async.contract;

import com.example.classManagementAppServer.dto.ai.*;

public interface ChatRatingAsyncContract {
    void chatRating (String userMessage, String botMessage);
}
