package com.example.classManagementAppServer.repository.passwordReset;

import com.example.classManagementAppServer.annotation.sql.UniqueConstraintAnnotation;
import com.example.classManagementAppServer.dto.passwordReset.PasswordResetCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Primary
public class PasswordResetCodeRepositoryImpl implements PasswordResetCodeRepositoryContract
{
    private final JdbcTemplate jdbcTemplate;

    @Override
    @UniqueConstraintAnnotation
    public boolean postPasswordResetCode(PasswordResetCode passwordResetCode)
    {
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES(?, ?, ?)",
                PasswordResetCodeRepositoryContract.tableName,
                PasswordResetCodeRepositoryContract.usernameClmName,
                PasswordResetCodeRepositoryContract.codeClmName,
                PasswordResetCodeRepositoryContract.dateTimeClmName);

        return jdbcTemplate.update(sql, passwordResetCode.getUsername(), passwordResetCode.getCode(), passwordResetCode.getLocalDateTime()) > 0;
    }

    @Override
    public PasswordResetCode getPasswordResetCodeByUsername(String username) {

        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                PasswordResetCodeRepositoryContract.tableName,
                PasswordResetCodeRepositoryContract.usernameClmName);

        RowMapper<PasswordResetCode> rawMapper = (rs, rn) ->
        {
            PasswordResetCode passwordResetCode = new PasswordResetCode();

            passwordResetCode.setId(rs.getInt(PasswordResetCodeRepositoryContract.idClmName));
            passwordResetCode.setUsername(rs.getString(PasswordResetCodeRepositoryContract.usernameClmName));
            passwordResetCode.setCode(rs.getString(PasswordResetCodeRepositoryContract.codeClmName));
            passwordResetCode.setLocalDateTime(rs.getObject(PasswordResetCodeRepositoryContract.dateTimeClmName, LocalDateTime.class));

            return passwordResetCode;
        };

        List<PasswordResetCode> passwordResetCodeList = jdbcTemplate.query(sql, rawMapper, username);

        return passwordResetCodeList.isEmpty() ? null : passwordResetCodeList.getFirst();
    }

    @Override
    public boolean deletePasswordResetCodeByUsername(String username)
    {
        String sql = String.format("DELETE FROM %s WHERE %s=?",
                PasswordResetCodeRepositoryContract.tableName,
                PasswordResetCodeRepositoryContract.usernameClmName);

        return jdbcTemplate.update(sql, username) > 0;
    }
}
