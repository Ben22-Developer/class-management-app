 package app.classmanagementappclient.Backend.Database.Create;

 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAssessment.InsertAssessment;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAssessmentRecord.InsertAssessmentRecord;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAttendance.InsertAttendance;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertAttendanceRecord.InsertAttendanceRecord;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertChatRecord.InsertChatCredentials;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertChatRecord.InsertTeacher_BotChat;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClass.InsertClass;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassLesson.InsertClassLesson;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassTerm.InsertClassTerm;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertClassYear.InsertClassYear;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertSchool.InsertSchool;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudent.InsertStudent;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertStudentClass.InsertStudentClass;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertTeacher.InsertTeacher;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertTeacherMessage.InsertTeacherMessage;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.InsertWebAPIKey.InsertAPIKey;
 import app.classmanagementappclient.Backend.Database.Create.ObjectsInsert.ObjectsInsert;
 import app.classmanagementappclient.Backend.Database.DatabaseManipulation;
 import app.classmanagementappclient.Backend.Database.UtilityClass;
 import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;
 import app.classmanagementappclient.Backend.Objects.AssessmentRecord.AssessmentRecord;
 import app.classmanagementappclient.Backend.Objects.Attendance.Attendance;
 import app.classmanagementappclient.Backend.Objects.AttendanceRecord.AttendanceRecord;
 import app.classmanagementappclient.Backend.Objects.Class.Classs;
 import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
 import app.classmanagementappclient.Backend.Objects.School.School;
 import app.classmanagementappclient.Backend.Objects.Student.Student;
 import app.classmanagementappclient.Backend.Objects.StudentClass.StudentClass;
 import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
 import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;
 import app.classmanagementappclient.Backend.Objects.WebObject.ApiKey;
 import app.classmanagementappclient.Backend.Objects.WebObject.Chat.Chat;
 import app.classmanagementappclient.Backend.Objects.WebObject.Chat.ChatCredentials;
 import app.classmanagementappclient.Backend.Objects.WebObject.ContactUs.TeacherMessage;
 import app.classmanagementappclient.Backend.Objects.Year.ClassYear;

 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;

 /**
 *
 * @author BEN
 */
public class InsertDatabaseManipulation extends DatabaseManipulation
{
    private final Object object;
    private String errorMsg;
    
    public InsertDatabaseManipulation(Object object) throws SQLException 
    {
        super(); 
        this.object = object;
    }
    
    public boolean execute () {
        Connection connection = super.getConnection();
        ObjectsInsert objectInsert = null;
        boolean bool = false;
        
        try 
        {
            if (object instanceof Teacher) 
                objectInsert = new InsertTeacher(connection, object);
            
            else if (object instanceof Student) 
                objectInsert = new InsertStudent (connection, object);
            
            else if (object instanceof School)
                objectInsert = new InsertSchool(connection, object);
            
            else if (object instanceof Classs) 
                objectInsert = new InsertClass (connection, object);

            else if (object instanceof ClassYear) 
                objectInsert = new InsertClassYear (connection, object);
            
            else if (object instanceof ClassTerm) 
                objectInsert = new InsertClassTerm (connection, object);            

            else if (object instanceof ClassLesson) 
                objectInsert = new InsertClassLesson (connection, object); 
            
            else if (object instanceof StudentClass) 
                objectInsert = new InsertStudentClass (connection, object);
            
            else if (object instanceof Attendance) 
                objectInsert = new InsertAttendance (connection, object); 
            
            else if (object instanceof AttendanceRecord) 
                objectInsert = new InsertAttendanceRecord (connection, object);
            
            else if (object instanceof Assessment) 
                objectInsert = new InsertAssessment (connection, object);  
            
            else if (object instanceof AssessmentRecord) 
                objectInsert = new InsertAssessmentRecord (connection, object);     
            
            else if (object instanceof ApiKey)
                objectInsert = new InsertAPIKey (connection, object);  
            
            else if (object instanceof ChatCredentials)
                objectInsert = new InsertChatCredentials(connection, object);
            
            else if (object instanceof Chat)
                objectInsert = new InsertTeacher_BotChat(connection, object);            

            else if (object instanceof TeacherMessage)
                objectInsert = new InsertTeacherMessage(connection, object);
            
            bool = objectInsert.insert();
        }
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        finally {
            rememberToCloseConnection();
        }
        
        if (!bool) {
            setErrorMsg(objectInsert.getErrorMsg());
        }
        return bool;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public static boolean executePrepareStatement (PreparedStatement statement, String...objectName) throws SQLException 
    {
        UtilityClass uc = new UtilityClass();
        boolean inserted = uc.executeDMLStament(statement);
        
        if (objectName.length != 0) 
        {
            System.out.println("---------------------------------------------------------------");
            System.out.println( objectName[0] +" Insertion succedded");        
            System.out.println("---------------------------------------------------------------");
        }   
        return inserted;
    }

    @Override
    public void rememberToCloseConnection() {
        try {
            closeConnection();
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
}
