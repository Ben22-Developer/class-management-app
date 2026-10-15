package app.classmanagementappclient.Backend.Database.Read.ReadClassData.InterfaceImplementation.Names;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadAllClassesProxy implements ReadStringInterface 
{
    private final List<String> classNames = new ArrayList<>();
    
    @Override
    public String getQuery() {
        return """
               SELECT DISTINCT classData.className
               FROM classData
               JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
               JOIN teacherPersonalData ON schoolData.teacherNum = teacherPersonalData.teacherNum
               WHERE teacherPersonalData.teacherNum = ? ORDER BY classData.className
               """;
    }    

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        setStatement(statement, uc);
        uc.getStringsStatementByClmRes(statement, 1, classNames);
    }    
    
    @Override
    public List<String> getData() {
        return classNames;
    }    

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }
}
