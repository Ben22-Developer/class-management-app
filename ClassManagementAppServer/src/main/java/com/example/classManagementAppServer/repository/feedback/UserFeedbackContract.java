package com.example.classManagementAppServer.repository.feedback;

import com.example.classManagementAppServer.dto.message.*;

import java.util.*;

public interface UserFeedbackContract {
    boolean postUserFeedback(TeacherMessage teacherMessage);
    List<TeacherMessage> getUserFeedback();
    boolean deleteUserFeedback(int id);
}
