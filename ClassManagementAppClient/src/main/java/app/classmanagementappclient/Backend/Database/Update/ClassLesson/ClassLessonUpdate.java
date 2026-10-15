package app.classmanagementappclient.Backend.Database.Update.ClassLesson;

import app.classmanagementappclient.Backend.Database.Update.UpdateData;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author BEN
 */
public class ClassLessonUpdate extends UpdateData {

    private final ClassLesson currData;
    private final ClassLesson newData;
    private final ArrayList<String> toUpdateClms = new ArrayList<>();
    private int classLessonNum;

    public ClassLessonUpdate (ClassLesson currData, ClassLesson newData) throws SQLException {
        this.currData = currData;
        this.newData = newData;
    }    
    
    public boolean updateData () {
        return super.updateData("classLessonData");
    }         
    
    @Override
    public boolean duplicateChecker() throws SQLException {
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();        
        String query = """
                        SELECT classLessonNum FROM classLessonData WHERE lessonName = ? AND classTermNum = ?
                       """;
        int res;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, newData);
            res = uc.getStatementIntResult(statement, 1);
        }
        return res != Integer.MIN_VALUE;           
    }

    @Override
    public void duplicateCloser() {
        
        if (newData.getTerm() == null) { // this is because of ClassPanel/classLessonsPanel, it can be a long to retrive all needed data for term with only it's num
            super.setErrorMsg("Lesson: "+newData.getLessonName()+" is already in the class");
            return;
        }
        
        String string = newData.getTerm().getClassYear().getClasss().getSchool().getSchoolName() +"/ "+ newData.getTerm().getClassYear().getClasss().getClassName() + 
                        "/ Year " + newData.getTerm().getClassYear().getYear() + "/ Term " + newData.getTerm() +"/ Lesson " + newData.getLessonName() + " already exists!";
        super.setErrorMsg(string);
        super.rememberToCloseConnection();    
    }

    @Override
    public void setDetailsToUpdate() {
        toUpdateClms.add("lessonName");
        try {
            setClassLessonNum();
        } catch (SQLException ex) {
            System.getLogger(ClassLessonUpdate.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void setClassLessonNum () throws SQLException {
        String query = """
                        SELECT classLessonNum FROM classLessonData WHERE lessonName = ? AND classTermNum = ?
                       """;
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement, currData);
            classLessonNum = uc.getStatementIntResult(statement, 1);
        }        
    }    
    
    @Override
    public boolean updateDetails(String query) throws SQLException {
        
        Connection connection = super.getConnection();
        UtilityClass uc = super.getUc();        
        query = query + "WHERE classLessonNum = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatement(statement);
            return uc.executeDMLStament(statement);
        }       
    }

    @Override
    public ArrayList<String> getUpdateClms() {
        return toUpdateClms;
    }

    private void setStatement(PreparedStatement statement, ClassLesson classLesson) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, classLesson.getLessonName(), 1);
        uc.setIntStatement(statement, classLesson.getClassTermNum(), index);
    }

    private void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, newData.getLessonName(), 1);
        uc.setIntStatement(statement, classLessonNum, index);
    }
}
