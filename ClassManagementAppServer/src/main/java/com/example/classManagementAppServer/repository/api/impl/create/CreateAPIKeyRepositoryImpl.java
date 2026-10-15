package com.example.classManagementAppServer.repository.api.impl.create;

import com.example.classManagementAppServer.annotation.sql.*;
import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.api.contract.create.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class CreateAPIKeyRepositoryImpl  implements CreateAPIKeyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @UniqueConstraintAnnotation
    public void createAPIKeyConfirmRecord (UserBuilder userBuilder) {

        String sql =
                """
                    INSERT INTO api_key_confirm(username, password, token_category) VALUES (?,?,?)
                """;
        jdbcTemplate.update(sql, userBuilder.getUsername(), passwordEncoder.encode( userBuilder.getPassword() ), userBuilder.getTokenCategory().toString());
    }
}
