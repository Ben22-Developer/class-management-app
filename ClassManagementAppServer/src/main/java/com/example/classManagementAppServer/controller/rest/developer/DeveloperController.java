package com.example.classManagementAppServer.controller.rest.developer;

import com.example.classManagementAppServer.dto.secret_key.*;
import com.example.classManagementAppServer.service.rest.ai.*;
import jakarta.websocket.server.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/developer")
public class DeveloperController {

    private final VectorStoreService vectorStoreService;

    private final TokenSplitterTestService tokenSplitterTestService;

    @PostMapping("/ai/data")
    @PreAuthorize("@preAuthorizeService.verifyDeveloperSecretKey(#developerSecretKey)")
    public ResponseEntity<?> aiDataPost (@RequestBody DeveloperSecretKey developerSecretKey) {
        return ResponseEntity.ok(vectorStoreService.store());
    }

    @GetMapping("/ai/split/test/{developerSecretKey}")
    @PreAuthorize("@preAuthorizeService.verifyDeveloperSecretKey(#developerSecretKey)")
    public ResponseEntity<?> aiSplitTest (@PathVariable String developerSecretKey) {
        return ResponseEntity.ok(tokenSplitterTestService.tokenSplitterTest());
    }
}
