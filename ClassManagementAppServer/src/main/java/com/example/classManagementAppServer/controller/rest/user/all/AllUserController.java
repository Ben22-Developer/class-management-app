package com.example.classManagementAppServer.controller.rest.user.all;

import com.example.classManagementAppServer.dto.passwordReset.PasswordResetCode;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.service.passwordReset.PasswordResetCodeService;
import com.example.classManagementAppServer.service.rest.schedule.test.*;
import com.example.classManagementAppServer.service.rest.api.contract.*;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@RestController
@RequestMapping("/all")
@RequiredArgsConstructor
public class AllUserController {

    @Resource(name="apiKeyClaimImpl")
    private APIKeyClaimContract apiKeyClaimContract;

    @Resource(name="apiKeyConfirmImpl")
    private APIKeyConfirmContract apiKeyConfirmContract;

    private final UserSchedulingTest userSchedulingTest; // here for only testing purpose


    private final PasswordResetCodeService passwordResetCodeService;

    @GetMapping("/test")
    public boolean testConnection () {
        return true;
    }

    @PostMapping("/free/api/claim")
    public ResponseEntity<?> freeTierAPIKeyClaim (@RequestBody UserBuilder userBuilder) {
//        return "Hello " + userBuilder;
        return apiKeyClaimContract.freeTierApiKeyClaim(userBuilder);
    }

    @PostMapping("/free/api/confirm")
    public ResponseEntity<?> freeTierAPIKeyConfirm (@RequestBody UserBuilder userBuilder) {
        return apiKeyConfirmContract.freeTierAPIKeyConfirm(userBuilder);
    }

    @PostMapping("/paid/api/claim")
    public ResponseEntity<?> paidTierAPIKeyClaim (@RequestBody UserBuilder userBuilder) {
        return apiKeyClaimContract.paidApiKeyClaim(userBuilder);
    }

    @PostMapping("/test/post")
    public String test () {
        return "test post";
    }

    @GetMapping("/test/start/schedule")
    public ResponseEntity<?> startScheduleTest () {
        return userSchedulingTest.startSchedule();
    }

    @PostMapping("/password-reset-code")
    public ResponseEntity<?> postPasswordResetCode (@RequestBody Username username)
    {
        passwordResetCodeService.postPasswordResetCode(username.getUsername());
        return ResponseEntity.ok("Your password reset code is sent to your email.");
    }

    @DeleteMapping("/password-reset-code")
    public ResponseEntity<?> checkPasswordResetCode (@RequestBody PasswordResetCode passwordResetCode)
    {
        passwordResetCodeService.passwordResetCodeSubmit(passwordResetCode);
        return ResponseEntity.ok("You can now reset your password.");
    }

    @DeleteMapping("/test/cancel/schedule")
    public ResponseEntity<?> cancelScheduleTest (@RequestParam String uuid) {
        return userSchedulingTest.cancelSchedule(uuid);
    }
}
