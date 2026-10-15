package com.example.classManagementAppServer.repository.user.impl.read;

import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;
import com.example.classManagementAppServer.repository.user.contract.read.*;
import com.example.classManagementAppServer.service.security.authentication.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ReadUserRepositoryImpl implements ReadUserRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.security.user.role.admin}")
    private String adminRole;

    @Override
    public void readUserSecurityDetails(MyUserDetails myUserDetails) {

        String sql = "SELECT id, username, password FROM user WHERE username = ?";

        RowMapper<Void> mapper = (resultSet, rowNbr) ->
        {
            myUserDetails.setUserID(resultSet.getInt(1));
            myUserDetails.setUsername(resultSet.getString(2));
            myUserDetails.setUserPassword(resultSet.getString(3));
            return null;
        };

        jdbcTemplate.queryForObject(sql, mapper, myUserDetails.getUsername());
    }

    @Override
    public void readUserGrantedAuthorities(MyUserDetails myUserDetails, List<GrantedAuthority> authorities) {

        int id = myUserDetails.getUserBuilder().getId();

        String role = readUserRole(id);

        authorities.add(() -> role);

        if (role.equals(adminRole))
            return;

        Optional<?> optional = Optional.ofNullable( readUserTokenType(id));
        boolean isPresent = optional.isPresent();

        if (!isPresent)
            return;

        TokenCategory tokenCategory = TokenCategory.valueOf( (String) optional.get() );
        authorities.add(() -> tokenCategory.toString());

    }

    @Override
    public int readUserIDByUsername(String username) {

        String sql = "SELECT id FROM user WHERE username = ?";

        RowMapper<Integer> mapper = (resultSet, rowNbr) -> resultSet.getInt(1);

        return jdbcTemplate.queryForObject(sql, mapper, username);
    }

    @Override
    public String readUserTokenType(int id) {

        String sql =
                """
                    SELECT token_type.category FROM token_type  
                    JOIN user_token_type ON user_token_type.token_type_id = token_type.id
                    WHERE user_token_type.user_id = ?
                """;
        RowMapper<String> mapper = (resultSet, rowNbr) -> resultSet.getString(1);

        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public int readUserTokenAmount(int id) {

        String sql = "SELECT amount FROM user_token_amount WHERE user_id = ?";

        RowMapper<Integer> mapper = (resultSet, rowNbr) -> resultSet.getInt(1);

        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public String readUserRole(int id) {

        String sql =
        """
            SELECT role.role FROM role 
            JOIN user_role ON role.id = user_role.role_id
            JOIN user ON user.id = user_role.user_id
            WHERE user.id = ?
        """;

        RowMapper<String> mapper = (resultSet, rowNbr) -> resultSet.getString(1);

        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<UserBuilder> getAllUsername_id() {

        String sql = "SELECT id, username  FROM user;";

        List<UserBuilder> users = new ArrayList<>();

        RowMapper<Void> rowMapper = getUsersRowMapper(users);

        jdbcTemplate.query(sql, rowMapper);

        return users;
    }

    @Override
    public List<UserBuilder> search (String search) {

        String sql = "SELECT id, username  FROM user WHERE username LIKE ?";

        List<UserBuilder> users = new ArrayList<>();

        RowMapper<Void> rowMapper = getUsersRowMapper(users);

        jdbcTemplate.query(sql, rowMapper, "%"+search+"%");

        return users;
    }

    public UserBuilder getUsername_id (int id) {

        String sql = "SELECT id, username  FROM user WHERE id=?;";

        List<UserBuilder> users = new ArrayList<>();

        RowMapper<Void> rowMapper = getUsersRowMapper(users);

        jdbcTemplate.query(sql, rowMapper, id);

        return users.isEmpty() ? null : users.getFirst();
    }

    private RowMapper<Void> getUsersRowMapper (List<UserBuilder> users)
    {
        return (rs,rn) -> {

            int iD = rs.getInt(1);
            String username = rs.getString(2);

            UserBuilder userBuilder = new UserBuilder().setId(iD).setUsername(username);
            users.add(userBuilder);

            return null;
        };
    }
}
