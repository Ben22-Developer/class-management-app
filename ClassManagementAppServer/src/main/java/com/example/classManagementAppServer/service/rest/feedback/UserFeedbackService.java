package com.example.classManagementAppServer.service.rest.feedback;

import com.example.classManagementAppServer.dto.message.*;
import com.example.classManagementAppServer.repository.feedback.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserFeedbackService {

    private final UserFeedbackContract userFeedbackContract;

    public boolean postUserFeedback(TeacherMessage teacherMessage) {
        return userFeedbackContract.postUserFeedback(teacherMessage);
    }

    public List<TeacherMessage> getUserFeedback() {
        return userFeedbackContract.getUserFeedback();
    }

    public boolean deleteUserFeedback(int id) {
        return userFeedbackContract.deleteUserFeedback(id);
    }
}
