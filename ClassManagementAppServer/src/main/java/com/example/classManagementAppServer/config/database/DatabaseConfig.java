package com.example.classManagementAppServer.config.database;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;

@Configuration
public class DatabaseConfig {


    @Bean
    @Primary
    public DataSource mySQLDatasource (@Value("${spring.datasource1.url}") String url,
                                       @Value("${spring.datasource1.password}") String password,
                                       @Value("${spring.datasource1.username}") String username)
    {
        var hikariDatasource = new HikariDataSource();

        hikariDatasource.setUsername(username);
        hikariDatasource.setPassword(password);

        hikariDatasource.setJdbcUrl(url);
        hikariDatasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return hikariDatasource;
    }

    @Bean
    public DataSource mariaDBDatasource (@Value("${spring.datasource2.url}") String url,
                                         @Value("${spring.datasource2.password}") String password,
                                         @Value("${spring.datasource2.username}") String username)
    {
        var hikariDatasource = new HikariDataSource();

        hikariDatasource.setUsername(username);
        hikariDatasource.setPassword(password);

        hikariDatasource.setJdbcUrl(url);
        hikariDatasource.setDriverClassName("org.mariadb.jdbc.Driver");
        return hikariDatasource;
    }

    @Bean
    @Primary
    public JdbcTemplate sqlJdbcTemplate (DataSource dataSource) {
        var hikariDatasource = (HikariDataSource) dataSource;
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate mariaDBJdbcTemplate (@Qualifier("mariaDBDatasource") DataSource dataSource) {
        var hikariDatasource = (HikariDataSource) dataSource;
        return new JdbcTemplate(dataSource);
    }



}

/*
*  spring.datasource1.driver-class-name
 spring.datasource1.url
    spring.datasource1.username
 spring.datasource1.password
* */
