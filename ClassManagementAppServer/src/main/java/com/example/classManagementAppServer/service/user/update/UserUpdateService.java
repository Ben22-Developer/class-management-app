package com.example.classManagementAppServer.service.user.update;

import com.example.classManagementAppServer.annotation.sql.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.user.contract.update.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserSecurityContext userSecurityContext;
    private final UserUpdateRepositoryContract userUpdateRepositoryContract;

    @InvalidUsernameAnnotation
    public ResponseEntity<?> updateUsernameByUsername (Username username)
    {
        return ResponseEntity.ok("Error found in this implementation");

//        boolean updated = userUpdateRepositoryContract.updateUsername(username, userSecurityContext.getUsername()); // note that security context holds admin username
//
//        if (updated)
//            return ResponseEntity.ok("Username successfully set to " + username.getUsername());
//        else
//            return ResponseEntity.ok("Nothing to update on the username ");
    }


    public ResponseEntity<?> updateUsernameByID (UserBuilder userBuilder)
    {
        boolean updated = userUpdateRepositoryContract.updateUsername(userBuilder.getUsername(), userBuilder.getId());

        if (updated)
            return ResponseEntity.ok("Username successfully set to " + userBuilder.getUsername());
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There is nothing to update");
    }
}
