package com.example.classManagementAppServer.dto.ui;

import lombok.*;

import java.time.*;

@Data
public class ChartDate {

    private LocalDate startingDate;
    private LocalDate endingDate;
}
