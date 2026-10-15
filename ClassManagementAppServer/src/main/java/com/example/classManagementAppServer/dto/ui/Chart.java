package com.example.classManagementAppServer.dto.ui;

import com.google.gson.*;

import java.util.*;


public class Chart {
    private final List<String> labels;
    private final List<Integer> data;
    private final List<String> backgroundColors;
    private final String type;

    public Chart(List<String> labels, List<Integer> data, List<String> backgroundColors, String type) {

        this.labels = labels;
        this.data = data;
        this.backgroundColors = backgroundColors;
        this.type = type;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Integer> getData() {
        return data;
    }

    public List<String> getBackgroundColors() {
        return backgroundColors;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
