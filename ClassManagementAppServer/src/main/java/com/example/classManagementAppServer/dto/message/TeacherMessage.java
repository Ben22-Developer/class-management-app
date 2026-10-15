package com.example.classManagementAppServer.dto.message;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.sql.*;

@Data
public class TeacherMessage {
    private int id;
    private String username;
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy, h:mm:ss a")
    private Timestamp timestamp;
}
