package com.example.classManagementAppServer.repository.user.impl.delete;

import com.example.classManagementAppServer.repository.user.contract.delete.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@Primary
@RequiredArgsConstructor
public class DeleteUserRepositoryImpl implements DeleteUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean deleteUserByUsername(String username) {
        String sql = "DELETE FROM user WHERE username = ?";
        return jdbcTemplate.update(sql, username) > 0;
    }
}
