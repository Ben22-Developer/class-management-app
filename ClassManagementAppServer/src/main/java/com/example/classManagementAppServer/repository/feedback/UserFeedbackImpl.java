package com.example.classManagementAppServer.repository.feedback;

import com.example.classManagementAppServer.dto.message.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserFeedbackImpl implements UserFeedbackContract{

    private final JdbcTemplate template;

    @Override
    public boolean postUserFeedback(TeacherMessage teacherMessage) {

        String sql = "INSERT INTO user_feedback(username, feedback, timestamp) VALUES(?,?,?)";
        return template.update(sql, teacherMessage.getUsername(), teacherMessage.getMessage(), teacherMessage.getTimestamp()) > 0;
    }

    @Override
    public List<TeacherMessage> getUserFeedback() {

        String sql = "SELECT * FROM user_feedback ORDER BY username";

        List<TeacherMessage> feedbacks = new ArrayList<>();

        RowMapper<Void> rowMapper = (rs, rn) ->
        {
            int id = rs.getInt(1);
            String username = rs.getString(2);
            String feedback = rs.getString(3);
            Timestamp timestamp = rs.getTimestamp(4);

            var teacherMessage = new TeacherMessage(feedback);
            teacherMessage.setId(id);
            teacherMessage.setUsername(username);
            teacherMessage.setTimestamp(timestamp);

            feedbacks.add(teacherMessage);
            return null;
        };

        template.query(sql, rowMapper);

        return feedbacks;
    }

    @Override
    public boolean deleteUserFeedback(int id) {
        String sql = "DELETE FROM user_feedback WHERE id=?";
        return template.update(sql, id) > 0;
    }
}
