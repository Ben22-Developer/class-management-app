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

public class ReadStudentsOnSpecificAssessmentRecord  implements ReadStringOfStringInterface {

    private final int assessmentNum;
    private final List<List<String>> data = new ArrayList<>();

    public ReadStudentsOnSpecificAssessmentRecord (int assessmentNum) {
        this.assessmentNum = assessmentNum;
    }

    @Override
    public List<List<String>> getData() {
        return data;
    }

    @Override
    public String getQuery() {
        return """
                    SELECT ROW_NUMBER() OVER(ORDER BY studentPersonalData.studentName) AS RowNums , studentPersonalData.studentID, studentPersonalData.studentName, studentAssessmentRecordData.studentMarks FROM studentPersonalData
                    JOIN studentClassData ON studentClassData.studentNum = studentPersonalData.studentNum
                    JOIN StudentAssessmentRecordData ON StudentAssessmentRecordData.studentClassNum = studentClassData.studentClassNum
                    WHERE StudentAssessmentRecordData.AssessmentNum = ? ORDER BY studentPersonalData.studentName, studentAssessmentRecordData.studentMarks
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getStringsStatementByRowRes(statement, data);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.setIntStatement(statement, assessmentNum, 1);
    }
}
