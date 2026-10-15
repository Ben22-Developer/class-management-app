package com.example.classManagementAppServer.repository.ai.implentation;

import com.example.classManagementAppServer.dto.ai.*;
import com.example.classManagementAppServer.repository.ai.contract.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class AIRepositoryImpl implements AIRepositoryContract {

    private final JdbcTemplate jdbcTemplate;
    private String tableName = "chat_rating";

    @Override
    public boolean insertChatRating(ChatRatingPost chatRatingPost) {

        String sql = "INSERT INTO " + tableName + "(user_message, bot_message, rate) VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql,chatRatingPost.getUserMessage(), chatRatingPost.getBotMessage(), chatRatingPost.getRate()) > 0;
    }

    @Override
    public List<ChatRating> readChatRating() {

        String sql = "SELECT id, user_message, bot_message, rate, DATE_FORMAT(datetime, '%d-%m-%Y %H:%i:%s') FROM " + tableName;

        List<ChatRating> chatRatingList = new ArrayList<>();

        RowMapper<Void> rowMapper = (rs, rn) -> {

            int id = rs.getInt(1);

            String userMessage = rs.getString(2);

            String botMessage = rs.getString(3);

            double rate = rs.getDouble(4);

            String localDateTime = rs.getString(5);

            chatRatingList.add( new ChatRating(id, userMessage, botMessage, rate, localDateTime) );

            return null;
        };

        jdbcTemplate.query(sql, rowMapper);

        return chatRatingList;
    }

    @Override
    public void deleteChatRating(List<Integer> id) {

        for (int anId : id)
            deleteChatRating(anId);
    }

    @Override
    public boolean deleteChatRating(int id) {

        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        return jdbcTemplate.update(sql,id) > 0;
    }
}
