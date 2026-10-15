package com.example.classManagementAppServer.controller.rest.admin;

import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.service.rest.admin.*;
import com.example.classManagementAppServer.service.rest.feedback.*;
import com.example.classManagementAppServer.service.rest.schedule.user.contract.*;
import com.example.classManagementAppServer.service.rest.statistics.*;
import com.example.classManagementAppServer.service.user.update.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserUpdateService userUpdateService;

    private final UserScheduleReadServiceContract userScheduleReadServiceContract;

    private final ChatRatingService chatRatingService;

    private final StatisticsService statisticsService;

    private final ReadUsersAdminService readUsersAdminService;

    private final UserFeedbackService userFeedbackService;

    @GetMapping("/get/uuid/future/map")
    public HashMap<String, ScheduledFuture> getUUIDToScheduledFutureMap () {
        return userScheduleReadServiceContract.getUUIDToScheduledFutureMap();
    }

    @PutMapping("/username/update")
    public ResponseEntity<?> userNameUpdate (Username username) {
        return userUpdateService.updateUsernameByUsername(username);
    }

    @GetMapping("/chat-rating")
    public ResponseEntity<?> getAllChatRating () {
        return chatRatingService.getAllChatRating();
    }

    @GetMapping("/statistics/token-usage")
    public ResponseEntity<?> tokenUsageDataset () {
        return ResponseEntity.ok(statisticsService.tokenUsageDataset());
    }

    @GetMapping("/statistics/chat-rating")
    public ResponseEntity<?> chatRatingDataset () {
        return ResponseEntity.ok(statisticsService.chatRatingDataset());
    }

    @GetMapping("/statistics/token-usage/search")
    public ResponseEntity<?> tokenUsageDatasetByDate (@RequestParam String startingDate, @RequestParam String endingDate) {
        return ResponseEntity.ok(statisticsService.tokenUsageDatasetByDate(startingDate, endingDate));
    }

    @GetMapping("/statistics/chat-rating/search")
    public ResponseEntity<?> chatRatingDatasetByDate (@RequestParam String startingDate, @RequestParam String endingDate) {
        return ResponseEntity.ok(statisticsService.chatRatingDatasetByDate(startingDate, endingDate));
    }

    @GetMapping("/users/username_id")
    public ResponseEntity<?> allUsername_id () {
        return ResponseEntity.ok(readUsersAdminService.allUsername_id());
    }

    @GetMapping("/users/username_id/search")
    public ResponseEntity<?> allUsername_id (@RequestParam String search) {
        return ResponseEntity.ok(readUsersAdminService.search(search));
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<?> getUserFeedbacks () {
        return ResponseEntity.ok(userFeedbackService.getUserFeedback());
    }

    @DeleteMapping("/feedbacks")
    public ResponseEntity<?> getUserFeedbacks (@RequestParam int id) {
        return ResponseEntity.ok(userFeedbackService.deleteUserFeedback(id));
    }
}
