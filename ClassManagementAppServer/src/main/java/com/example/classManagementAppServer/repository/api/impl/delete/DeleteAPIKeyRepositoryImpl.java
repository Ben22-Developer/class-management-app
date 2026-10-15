package com.example.classManagementAppServer.repository.api.impl.delete;

import com.example.classManagementAppServer.repository.api.contract.delete.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class DeleteAPIKeyRepositoryImpl implements DeleteAPIKeyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean deleteClaimedAPIKeyRecord(String password) {
        String sql = "DELETE FROM api_key_confirm WHERE password = ?";
        return jdbcTemplate.update(sql, password) >= 1;
    }
}
