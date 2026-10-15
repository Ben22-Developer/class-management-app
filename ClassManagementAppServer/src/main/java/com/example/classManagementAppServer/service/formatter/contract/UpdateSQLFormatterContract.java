package com.example.classManagementAppServer.service.formatter.contract;

import com.example.classManagementAppServer.exception.sql.*;

import java.util.*;

public interface UpdateSQLFormatterContract {

    String formatUpdateSQLQuery(String tableName, LinkedHashMap<String, String> clmToValueCurrData, LinkedHashMap<String, String> clmToValueNewData, String...whereClause);

    void updateInitialStatement (String tableName, StringBuilder builder);

    void makeComparison (LinkedHashMap<String, String> clmToValueCurrData, LinkedHashMap<String, String> clmToValueNewData, StringBuilder builder) throws NothingToUpdateException;

    void whereClauseStatement (String whereClause, StringBuilder builder);
}
