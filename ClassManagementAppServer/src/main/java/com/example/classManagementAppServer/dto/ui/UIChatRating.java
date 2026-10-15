package com.example.classManagementAppServer.dto.ui;

import com.example.classManagementAppServer.dto.ai.*;
import com.google.gson.*;
import lombok.*;

import java.util.*;

@RequiredArgsConstructor
@Getter
public class UIChatRating {
    private final List<ChatRating> chatRatingList;
    private final Set<Integer> toDeleteChatRatingID;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, UIChatRating.class);
    }
}
