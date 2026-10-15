package com.example.classManagementAppServer.service.formatter.impl;

import com.example.classManagementAppServer.exception.sql.*;
import com.example.classManagementAppServer.service.formatter.contract.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UpdateSQLFormatterImpl implements UpdateSQLFormatterContract {


    @Override
    public String formatUpdateSQLQuery
            (String tableName, LinkedHashMap<String, String> clmToValueCurrData, LinkedHashMap<String, String> clmToValueNewData, String...whereClause)
    {
        StringBuilder builder = new StringBuilder();

        updateInitialStatement(tableName, builder);
        makeComparison(clmToValueCurrData, clmToValueNewData, builder);

        if (whereClause.length != 0)
            whereClauseStatement(whereClause[0], builder);

        return builder.toString();
    }

    @Override
    public void updateInitialStatement(String tableName, StringBuilder builder) {
        builder.append("Update ").append(tableName).append(" SET");
    }

    @Override
    public void makeComparison(LinkedHashMap<String, String> clmToValueCurrData, LinkedHashMap<String, String> clmToValueNewData, StringBuilder builder) throws NothingToUpdateException
    {

        Set<Map.Entry<String, String>> currDataSet = clmToValueCurrData.entrySet();
        int toUpdate = 0;

        for (Map.Entry<String, String> currSet : currDataSet)
        {
            String clm = currSet.getKey();
            String currValue = currSet.getValue();
            String newValue = clmToValueNewData.get(clm);

            if (!currValue.equals(newValue))
            {
                builder.append(toUpdate == 0 ? "" : ",").append(" ").append(clm).append("=?");
                toUpdate += 1;
            }
            else
                clmToValueNewData.remove(clm);
        }

        if (toUpdate == 0 && clmToValueNewData.isEmpty()) // the '&&' is for being too sure
            throw new NothingToUpdateException("You have nothing to update!");
    }

    @Override
    public void whereClauseStatement(String whereClause, StringBuilder builder) {
        builder.append(" WHERE ").append(whereClause);
    }
}
