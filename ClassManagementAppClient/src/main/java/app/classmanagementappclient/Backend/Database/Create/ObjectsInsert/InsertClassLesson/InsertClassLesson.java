package app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassLesson;

import app.classmanagementappclient.Backend.Database.Create.InsertDatabaseManipulation;
import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author BEN
 */
public class InsertClassLesson extends ObjectsInsert
{
    private final ClassLesson lesson;
    private int classLessonNum;
    
    public InsertClassLesson (Connection connection, Object object) throws SQLException {
        super(connection, object);
        lesson = (ClassLesson) object;
    }   

    @Override
    public boolean insert() throws SQLException
    {
        boolean res = isClassLessonInserted();
        
        if (res) {
            setErrorMsg();
            return false;
        }
        try {
            res = executeInsert();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            setErrorMsg();
            return false;            
        }
                
        if (!res) {
            super.setErrorMsg("Application Error!\nAt InsertNewLesson.insert");
            return false;
        }
        
        setClassLessonNum();
        return true;
    }
    
    private boolean executeInsert () throws SQLException {
        Connection connection = super.getConnection();
        String query = "INSERT INTO classLessonData (lessonName, classTermNum) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        setStatement(statement);
        return InsertDatabaseManipulation.executePrepareStatement (statement, lesson.toString());        
    }

    @Override
    public void setStatement(PreparedStatement statement) throws SQLException {
        UtilityClass uc = super.getUc();
        int index = uc.setStringStatement(statement, lesson.getLessonName(), 1);
        uc.setIntStatement(statement, lesson.getClassTermNum(), index);
    }
    
    private boolean isClassLessonInserted() throws SQLException {
        setClassLessonNum();
        return classLessonNum != Integer.MIN_VALUE;
    }
    
    private void setErrorMsg () {
        
        ClassTerm term = lesson.getTerm();
        
        if (term == null) { //it's null when it comes to inputting the lessons via the ClassPanel/ LessonsPanel, for more details check Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewClassLessonsDialog.getSubmitBtn
            super.setErrorMsg("\nLesson : " + lesson.getLessonName() + " already exists in this class!\n");
            return;
        }
        super.setErrorMsg("\nTeacher: " + LoggedInTeacher.getLoggedInTeacher().getName() + " Num: " + LoggedInTeacher.getLoggedInTeacher().getTeacherNum() + 
                "\nAlready teaches at: " + term.getClassYear().getClasss().getSchool().getSchoolName() +
                "\nClass: "+ term.getClassYear().getClasss().getClassName() + " ClassNum: " + term.getClassYear().getClassNum() + 
                "\nYear: " + term.getClassYear().getYear() + 
                "\nTerm: " + term.getTerm() +
                "\nLesson: "+lesson.getLessonName() +" Lesson num: " + getClassLessonNum());        
    }

    public int getClassLessonNum() {
        return classLessonNum;
    }

    public void setClassLessonNum() throws SQLException {
        UtilityClass uc = super.getUc();
        classLessonNum = uc.getClassLessonNum(super.getConnection(), lesson.getClassTermNum(), lesson.getLessonName());
    }
}
