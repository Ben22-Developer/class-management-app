package com.example.classManagementAppServer.repository.user.impl.create;

import com.example.classManagementAppServer.annotation.sql.*;
import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.user.contract.create.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;


@Repository
@RequiredArgsConstructor
public class CreateUserRepositoryImpl implements CreateUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @InvalidSQLOperationAnnotation
    public void createUserAccount(User user) {

        String sql = "INSERT INTO user(username, password) VALUES(?,?)";
        jdbcTemplate.update(sql, user.username(), user.password());
    }

    @Override
    public void createUserRoleAccount(User user) {
        String sql = "INSERT INTO user_role(user_id, role_id) VALUES(?,1)";
        jdbcTemplate.update(sql, user.id());
    }

    @Override
    public void createUserTokenTypeAccount(User user, int tokenTypeID) {

        String sql = "INSERT INTO user_token_type(user_id, token_type_id) VALUE(?,?)";
        jdbcTemplate.update(sql, user.id(), tokenTypeID);
    }

    @Override
    public void createUserTokenAmountAccount(User user, int amount) {

        String sql = "INSERT INTO user_token_amount(user_id, token_amount) VALUES(?,?)";
        jdbcTemplate.update(sql, user.id(), amount);
    }

    @Override
    public void createPaidTokenValidity(User user, int subscriptionTime) {

        String sql = "INSERT INTO paid_token_validity(user_id, subscription_time) VALUES(?,?)";
        jdbcTemplate.update(sql, user.id(), subscriptionTime);
    }
}
