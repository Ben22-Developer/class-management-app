package com.example.classManagementAppServer.repository.statistics;

import com.example.classManagementAppServer.dto.ui.*;
import lombok.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class StatisticsRepositoryImpl implements StatisticsRepositoryContract {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Dataset getTokenUsageDataset() {

        String sql =
                        """
                            SELECT date_format(date, '%d-%m-%Y'), SUM(token_amount) FROM user_token_amount GROUP BY date;
                        """;

        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        RowMapper<Void> rowMapper = tokenUsageRowMapper(labels, data);

        jdbcTemplate.query(sql, rowMapper);
        return  new Dataset(labels, data);
    }

    @Override
    public Dataset getTokenUsageDatasetByDate (String startingDate, String endingDate) {

        String sql =
                """
                    SELECT date_format(date, '%d-%m-%Y'), SUM(token_amount) FROM user_token_amount
                    WHERE date > STR_TO_DATE(?, '%d-%m-%Y') AND date < STR_TO_DATE(?, '%d-%m-%Y')
                    GROUP BY date;
                """;

        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        RowMapper<Void> rowMapper = tokenUsageRowMapper(labels, data);

        jdbcTemplate.query(sql, rowMapper, startingDate, endingDate);
        return  new Dataset(labels, data);
    }

    @Override
    public Dataset getChatRatingDataset() {

        String sql =
                """
                        SELECT (fail/total)*100, (average/total)*100, (success/total)*100
                        FROM
                        (SELECT 
                        SUM(CASE WHEN rate < 5 THEN 1 ELSE 0 END) AS fail,
                        SUM(CASE WHEN rate >= 5 AND rate < 8 THEN 1 ELSE 0 END) AS average,
                        SUM(CASE WHEN rate >= 8 THEN 1 ELSE 0 END) AS success,
                        COUNT(rate) AS total
                        FROM chat_rating) AS sub_table;
                """;

        List<String> labels = List.of("Fail", "Average","Success");
        List<Integer> data = new ArrayList<>();

        RowMapper<Void> rowMapper = (rs, rn) -> {

            data.add(rs.getInt(1));
            data.add(rs.getInt(2));
            data.add(rs.getInt(3));

            return null;
        };

        jdbcTemplate.query(sql, rowMapper);
        return new Dataset(labels, data);
    }

    @Override
    public Dataset getChatRatingDatasetByDate (String startingDate, String endingDate) {

        String sql =
                """
                        SELECT (fail/total)*100, (average/total)*100, (success/total)*100
                        FROM
                        (SELECT
                        SUM(CASE WHEN rate < 5 THEN 1 ELSE 0 END) AS fail,
                        SUM(CASE WHEN rate >= 5 AND rate < 8 THEN 1 ELSE 0 END) AS average,
                        SUM(CASE WHEN rate >= 8 THEN 1 ELSE 0 END) AS success,
                        COUNT(rate) AS total
                        FROM chat_rating
                        WHERE datetime > STR_TO_DATE(?, '%d-%m-%Y') AND datetime < STR_TO_DATE(?, '%d-%m-%Y')
                        ) AS sub_table;
                """;

        List<String> labels = List.of("Fail", "Average","Success");
        List<Integer> data = new ArrayList<>();

        RowMapper<Void> rowMapper = chatRatingRowMapper(data);

        jdbcTemplate.query(sql, rowMapper, startingDate, endingDate);
        return new Dataset(labels, data);
    }

    private RowMapper<Void> chatRatingRowMapper (List<Integer> data) {

        return (rs, rn) -> {

            data.add(rs.getInt(1));
            data.add(rs.getInt(2));
            data.add(rs.getInt(3));

            return null;
        };
    }

    private RowMapper<Void> tokenUsageRowMapper (List<String> labels, List<Integer> data) {

        return (rs,rn) -> {

            labels.add(rs.getString(1));
            data.add(rs.getInt(2));

            return null;
        };
    }
}
