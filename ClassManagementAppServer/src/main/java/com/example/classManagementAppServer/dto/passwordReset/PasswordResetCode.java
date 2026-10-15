package com.example.classManagementAppServer.dto.passwordReset;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PasswordResetCode {

    private int id;
    private String code;
    private String username;
    private LocalDateTime localDateTime;
}