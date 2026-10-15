/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadAssessmentRecord.InterfaceImplementation.Details;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadStringOfStringInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadUnRecordedStudents implements ReadStringOfStringInterface {

    private final int assessmentNum;
    private final List<List<String>> data = new ArrayList<>();

    public ReadUnRecordedStudents (int assessmentNum) {
        this.assessmentNum = assessmentNum;
    }

    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return """
SELECT ROW_NUMBER() OVER(ORDER BY SPD.studentName) AS Num, SPD.studentID, SPD.studentName FROM studentPersonalData AS SPD
                    JOIN studentClassData ON SPD.studentNum = studentClassData.studentNum
                    JOIN classTermData ON classTermData.classTermNum = studentClassData.classTermNum
                    WHERE SPD.studentNum NOT IN 
                    (
                    	SELECT studentPersonalData.studentNum  FROM studentPersonalData 
                    	JOIN studentClassData ON studentClassData.studentNum = studentPersonalData.studentNum
                    	JOIN studentAssessmentRecordData ON studentAssessmentRecordData.studentClassNum = studentClassData.studentClassNum
                    	WHERE studentAssessmentRecordData.AssessmentNum = ?
                    )
                    AND classTermData.classTermNum =
                    (
                    	SELECT classTermData.classTermNum FROM classTermData
                    	JOIN classLessonData ON classLessonData.classTermNum = classTermData.classTermNum
                    	JOIN assessmentData ON assessmentData.LessonNum = classLessonData.classLessonNum
                    	WHERE assessmentData.AssessmentNum = ?
                    )
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntsStatement(statement, new Integer[] { assessmentNum, assessmentNum }, 1);
    }    
}
