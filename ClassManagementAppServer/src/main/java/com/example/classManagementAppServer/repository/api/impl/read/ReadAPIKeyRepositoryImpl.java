package com.example.classManagementAppServer.repository.api.impl.read;

import com.example.classManagementAppServer.annotation.sql.*;
import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.api.contract.read.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class ReadAPIKeyRepositoryImpl implements ReadAPIKeyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @InvalidSQLOperationAnnotation
    public UserBuilder getUnsavedUserByUsername(UserBuilder userBuilder) {

        String sql = "SELECT * FROM api_key_confirm WHERE username = ?";

        RowMapper<UserBuilder> mapper =
                (resultSet, rowNbr) ->
                {
                            UserBuilder userBuilder1 = new UserBuilder();
                            userBuilder1.setId(resultSet.getInt(1));
                            userBuilder1.setUsername(resultSet.getString(2));
                            userBuilder1.setUserPassword(resultSet.getString(3));
                            userBuilder1.setTokenCategory(resultSet.getString(4));
                            return userBuilder1;
                };
        return jdbcTemplate.queryForObject(sql, mapper, userBuilder.getUsername());
    }

    @Override
    public int readTokenTypeID (TokenCategory tokenCategory) {
        String sql = "SELECT id FROM token_type WHERE category = ?";
        RowMapper<Integer> mapper = (resultSet, rowNbr) -> resultSet.getInt(1);
        return jdbcTemplate.queryForObject(sql, mapper, tokenCategory.toString());
    }

    @Override
    public int readTokenNumAmount(int tokenTypeID) {

        String sql = "SELECT token_amount FROM token_type WHERE id = ?";
        RowMapper<Integer> mapper = (resultSet, rowNbr) -> resultSet.getInt(1);
        return jdbcTemplate.queryForObject(sql, mapper, tokenTypeID);
    }

    @Override
    public double readTokenCashAmount(int tokenTypeID) {
        String sql = "SELECT cash_amount FROM paid_token_cash_amount WHERE token_type_id = ?";
        RowMapper<Integer> mapper = (resultSet, rowNbr) -> resultSet.getInt(1);
        return jdbcTemplate.queryForObject(sql, mapper, tokenTypeID);
    }
}
