package com.example.classManagementAppServer.repository.user.impl.update;

import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.user.contract.update.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class UserUpdateRepositoryImpl implements UserUpdateRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean updateUsername(Username username, String oldUsername) {

        String sql = "UPDATE user SET username = ? WHERE username = ?";
        return jdbcTemplate.update(sql, username.getUsername(), oldUsername) > 0;
    }

    @Override
    public boolean updateUsername(String username, int id) {

        try {
            String sql = "UPDATE `user` SET username = ? WHERE id = ?";
            return jdbcTemplate.update(sql, username, id) > 0;
        }
        catch (Exception e){
            return false;
        }
    }
}
