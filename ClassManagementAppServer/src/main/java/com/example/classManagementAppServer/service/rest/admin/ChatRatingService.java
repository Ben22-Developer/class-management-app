package com.example.classManagementAppServer.service.rest.admin;

import com.example.classManagementAppServer.repository.ai.contract.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRatingService {

    private final AIRepositoryContract aiRepository;

    public ResponseEntity<?> getAllChatRating () {
        return ResponseEntity.ok(aiRepository.readChatRating());
    }


    public ResponseEntity<?> deleteChatRatings (List<Integer> ids) {
        aiRepository.deleteChatRating(ids);
        return ResponseEntity.ok("Deleted successfully");
    }
}
