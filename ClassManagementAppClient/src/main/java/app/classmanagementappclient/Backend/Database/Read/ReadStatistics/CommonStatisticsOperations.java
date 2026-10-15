package app.classmanagementappclient.Backend.Database.Read.ReadStatistics;

import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class CommonStatisticsOperations {
    
    public static String getCommonStatisticsQuery() {
        return 
                """
                
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

                    AND schoolData.schoolName = ?

                    AND classData.className = ?

                    AND classYearData.year = ?
                """;
    }
    
    public static String setTermsPlaceHolders (int numOfTerms) {
        return new UtilityClass().getWildCardsFormattedQuery("\nAND classTermData.Term IN (", numOfTerms, "");
    }
    
    public static String setLessonNamePlaceHolders (int numOfLessons) {
        return new UtilityClass().getWildCardsFormattedQuery("\nAND classLessonData.lessonName IN (", numOfLessons, "");
    }  

    public static String setStudentIDsPlaceHolders (int numOfStudentIDs) {
        return new UtilityClass().getWildCardsFormattedQuery("\nAND studentPersonalData.studentID IN (", numOfStudentIDs, "");
    }      
    
    public static int setCommonPlaceholders 
        (PreparedStatement statement, UtilityClass uc, String schoolName, String className, int classYear, List<Integer> classTerms, List<String> classLessons, List<String> studentIDs, int index) throws SQLException 
    {
        
        index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        index = uc.setIntStatement(statement, classYear, index);
        index = uc.setIntsStatement(statement, classTerms.toArray(), index);
        index = uc.setStringsStatement(statement, classLessons.toArray(), index);
        index = uc.setStringsStatement(statement, studentIDs.toArray(), index);        
        return index;
    }
    
    public static int setCommonClassStatisticsPlaceholders 
        (PreparedStatement statement, UtilityClass uc, String schoolName, String className, int classYear, int index) throws SQLException 
    {
        
        index = uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
        index = uc.setStringsStatement(statement, new String[] { schoolName, className }, index);
        index = uc.setIntStatement(statement, classYear, index);      
        return index;
    }        
}
