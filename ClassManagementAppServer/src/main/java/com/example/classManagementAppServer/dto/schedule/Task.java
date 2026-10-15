package com.example.classManagementAppServer.dto.schedule;

import lombok.*;

import java.time.*;

@Data
public class Task {
    private int id;
    private String title;
    private String description;
    private String uuid;
    private final LocalDate localDate;
}

