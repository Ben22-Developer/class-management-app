package com.example.classManagementAppServer.controller.rest.user.secure;

import com.example.classManagementAppServer.annotation.ai.*;
import com.example.classManagementAppServer.async.contract.*;
import com.example.classManagementAppServer.client.email.contract.*;
import com.example.classManagementAppServer.dto.ai.*;
import com.example.classManagementAppServer.dto.message.*;
import com.example.classManagementAppServer.dto.passwordReset.PasswordResetCode;
import com.example.classManagementAppServer.dto.schedule.*;

import com.example.classManagementAppServer.repository.schedule.contract.*;
import com.example.classManagementAppServer.service.passwordReset.PasswordResetCodeService;
import com.example.classManagementAppServer.service.rest.ai.*;
import com.example.classManagementAppServer.service.rest.api.contract.*;
import com.example.classManagementAppServer.service.rest.feedback.*;
import com.example.classManagementAppServer.service.rest.schedule.user.contract.*;
import com.example.classManagementAppServer.service.rest.token.*;
import com.example.classManagementAppServer.service.security.authentication.*;

import lombok.*;
import org.springframework.ai.chat.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/secure")
@RequiredArgsConstructor
public class SecuredUserController {

    private final DeleteUserService deleteUserService;

    private final UserSchedulePostServiceContract userSchedulePostServiceContract;
    private final UserScheduleUpdateServiceContract userScheduleUpdateServiceContract;
    private final UserScheduleDeleteServiceContract userScheduleDeleteServiceContract;

    private final UserSecurityContext userSecurityContext;

    private final UserScheduleReadRepositoryContract userScheduleReadRepository;

    private final TokenAmountService tokenAmountService;
    private final PromptHandlerService promptHandlerService;
    private final ChatRatingAsyncContract chatRatingAsync;

    private final UserFeedbackService userFeedbackService;


    @GetMapping("/test")
    public String test () {
        return "Secure test";
    }

    @DeleteMapping("/account/delete")
    public boolean deleteAccount () {
        return deleteUserService.deleteAccount();
    }

    @PostMapping("/post/schedule") // for testing purpose I can add @RequestParam String time ex '15:30:00'
    public ResponseEntity<?> postSchedule (@RequestBody Task task, @RequestParam(required = false, defaultValue = "10:10") String time) {
        return userSchedulePostServiceContract.startSchedule(task, time);
    }

    @GetMapping("/read/all/schedule")
    public ResponseEntity<List<Task>> readAllSchedule () {
        return ResponseEntity.ok(userScheduleReadRepository.readAllScheduledTask(userSecurityContext.getUserID()) );
    }

    @GetMapping("/read/all/archive/schedule")
    public ResponseEntity<List<Task>> readAllTask () {
        return ResponseEntity.ok(userScheduleReadRepository.readAllArchivedTask(userSecurityContext.getUserID()) );
    }

    @PutMapping("/put/schedule")
    public ResponseEntity<?> patchSchedule (@RequestPart Task currData, @RequestPart Task newData) {
        return userScheduleUpdateServiceContract.updateSchedule(currData, newData);
    }

    @DeleteMapping("/delete/schedule")
    public ResponseEntity<?> deleteSchedule (@RequestBody Task task) {
        return userScheduleDeleteServiceContract.deleteSchedule(task, userSecurityContext.getUserID());
    }

    @DeleteMapping("/delete/schedules")
    public ResponseEntity<?> deleteSchedule (@RequestBody List<Task> tasks) {
        return userScheduleDeleteServiceContract.deleteSchedule(tasks, userSecurityContext.getUserID());
    }

    @DeleteMapping("/delete/archive/schedules")
    public ResponseEntity<?> deleteArchiveSchedule (@RequestBody List<Task> tasks) {
        return userScheduleDeleteServiceContract.deleteArchivedSchedule(tasks, userSecurityContext.getUserID());
    }

    @PostMapping("/send/admin/message")
    public ResponseEntity<?> sendToAppMessage (@RequestBody TeacherMessage teacherMessage) {

        teacherMessage.setUsername(userSecurityContext.getUsername());
        return ResponseEntity.ok(userFeedbackService.postUserFeedback(teacherMessage));
    }

    @PostMapping("/ai/chat")
    @AIMaxTokenReachAnnotation
    public ResponseEntity<?> prompt (@RequestBody Prompt prompt)
    {

        tokenAmountService.tokenAmountCheck(userSecurityContext.getUserID());

        ResponseEntity<ChatResponse> result = (ResponseEntity<ChatResponse>) promptHandlerService.prompt(prompt);

        chatRatingAsync.chatRating(prompt.getPrompt(), String.valueOf(result.getBody()));

        tokenAmountService.deductTokenAmount(result.getBody().getMetadata().getUsage().getTotalTokens(), userSecurityContext.getUserID());

        return ResponseEntity.ok(result.getBody().getResult().getOutput().getText());
    }
}
