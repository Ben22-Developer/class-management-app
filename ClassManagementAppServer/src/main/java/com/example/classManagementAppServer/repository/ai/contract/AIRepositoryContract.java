package com.example.classManagementAppServer.repository.ai.contract;

import com.example.classManagementAppServer.dto.ai.*;

import java.util.*;

public interface AIRepositoryContract {

    boolean insertChatRating (ChatRatingPost chatRatingPost);

    List<ChatRating> readChatRating ();

    void deleteChatRating(List<Integer> id);

    boolean deleteChatRating(int id);
}
