/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord.InterfaceImplementation.ReadAllRecordedAssessment;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
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
public class ReadAllStudentAssessmentRecord implements ReadStringOfStringInterface{

    private final List<List<String>> data = new ArrayList<>();
    
    @Override
    public String getQuery() {
        return """
                SELECT DISTINCT schoolData.schoolName, classData.className, classYearData.Year  FROM studentAssessmentRecordData
                
                JOIN assessmentData ON assessmentData.assessmentNum = studentAssessmentRecordData.assessmentNum
                
                JOIN classLessonData ON classLessonData.classLessonNum = assessmentData.lessonNum 
                
                JOIN classTermData ON classTermData.classTermNum = classLessonData.classTermNum
                
                JOIN classYearData ON classYearData.classYearNum = classTermData.classYearNum
                
                JOIN classData ON classData.classNum = classYearData.classNum
                
                JOIN schoolData ON schoolData.schoolNum = classData.schoolNum
                
                JOIN teacherPersonalData ON teacherPersonalData.teacherNum = schoolData.teacherNum
                
                JOIN studentClassData ON studentClassData.studentClassNum = studentAssessmentRecordData.studentClassNum
                
                JOIN studentPersonalData ON studentPersonalData.studentNum = studentClassData.studentNum
                
                WHERE teacherPersonalData.teacherNum = ?
               """;
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
    }    
    
    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getStringsStatementByRowRes(statement, data);
    }
    
    @Override
    public List<List<String>> getData() {
        return data;
    }    
}
