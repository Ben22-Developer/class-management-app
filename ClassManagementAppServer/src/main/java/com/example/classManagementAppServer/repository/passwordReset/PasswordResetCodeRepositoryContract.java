package com.example.classManagementAppServer.repository.passwordReset;

import com.example.classManagementAppServer.dto.passwordReset.PasswordResetCode;

public interface PasswordResetCodeRepositoryContract {

    String tableName = "password_reset_code";
    String idClmName = "id";
    String usernameClmName = "username";
    String codeClmName = "code";
    String dateTimeClmName = "dateTime";

    boolean postPasswordResetCode (PasswordResetCode passwordResetCode);

    PasswordResetCode getPasswordResetCodeByUsername(String username);

    boolean deletePasswordResetCodeByUsername(String username);

}
