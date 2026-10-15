package com.example.classManagementAppServer.controller.mvc;

import java.security.*;
import java.util.*;

public class Messages {

    public static String adminMessage () {

        List<String> messages = List.of("Welcome back to the dashboard, Admin!", "Great to have you back at the helm.","Welcome back to the main control room!",
                "Your admin dashboard is fully loaded.", "Good to see you back online!", "Ready to manage? Welcome back, Admin.");

        return messages.get(new SecureRandom().nextInt(messages.size()));
    }

    public static String currentPage () {
        return "current_page";
    }

    public static String tokenChartString () {
        return "Token Usage";
    }

    public static String chatChartString () {
        return "Chat Rating";
    }

    public static String chartDateString () {
        return "chartDate";
    }

    public static String allUsername_IDString () {
        return "allUsername_IDS";
    }
}
