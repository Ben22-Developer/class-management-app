package com.example.classManagementAppServer.repository.token.impl;

import com.example.classManagementAppServer.repository.token.contract.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class TokenAmountRepositoryImpl implements TokenAmountRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deductTokenAmount(int tokens, int userID) {

        String sql = """
                UPDATE user_token_amount
                SET token_amount = token_amount + ?
                WHERE user_id = ? AND date = ?
                """;

        return jdbcTemplate.update(sql, tokens, userID, LocalDate.now()) > 0;
    }

    @Override
    public int readTokenAmount(int userID) {

        String sql = """
                SELECT token_amount
                FROM user_token_amount
                WHERE user_id = ? AND date = ?
                """;

        List<Integer> list = new ArrayList<>();

        RowMapper<Void> rowMapper = (rs, rn) -> {
            list.add(rs.getInt(1));
            return null;
        };

        jdbcTemplate.query(sql, rowMapper, userID, LocalDate.now());

        int tokenAmount = (list.size() == 0) ? 0 : list.getFirst();

        return tokenAmount;
    }

    @Override
    public boolean resetTokenAmount(int userID) {

        String sql = """
                INSERT INTO user_token_amount (user_id, token_amount, date)
                VALUES(?, ?, ?)
                """;

        return jdbcTemplate.update(sql, userID, 0, LocalDate.now()) > 0;
    }
}
