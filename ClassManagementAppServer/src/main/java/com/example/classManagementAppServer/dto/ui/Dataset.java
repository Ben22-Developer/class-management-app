package com.example.classManagementAppServer.dto.ui;

import lombok.*;

import java.util.*;

@Data
public class Dataset {
    private final List<String> labels;
    private final List<Integer> data;
}
