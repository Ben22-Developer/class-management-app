package com.example.classManagementAppServer.dto.user;

import com.example.classManagementAppServer.dto.enums.*;

public record User(int id, String username, String password, TokenCategory tokenCategory) {}
