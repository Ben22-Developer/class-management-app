package com.example.classManagementAppServer.service.security.authorization;

import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;

import com.example.classManagementAppServer.dto.secret_key.*;

@Service
@RequiredArgsConstructor
public class PreAuthorizeService {

    @Value("${spring.security.developer.secret_key}")
    private String developerSecretKey;
    private final UserSecurityContext userSecurityContext;

    public boolean verifyDeveloperSecretKey (DeveloperSecretKey secretKey) {
        return secretKey.getSecretKey().equals(developerSecretKey);
    }

    public boolean verifyDeveloperSecretKey (String secretKey) {
        return secretKey.equals(developerSecretKey);
    }

    public boolean verifyAdminUserRole () {
        var userRole =userSecurityContext.getUserRole();
        return userRole.compareTo("ROLE_ADMIN") == 0;
    }
}
