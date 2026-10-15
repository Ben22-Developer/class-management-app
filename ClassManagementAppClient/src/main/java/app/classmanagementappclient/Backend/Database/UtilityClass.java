
package app.classmanagementappclient.Backend.Database;

import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;
import app.classmanagementappclient.Backend.Objects.AssessmentRecord.AssessmentRecord;
import app.classmanagementappclient.Backend.Objects.Attendance.Attendance;
import app.classmanagementappclient.Backend.Objects.AttendanceRecord.AttendanceRecord;
import app.classmanagementappclient.Backend.Objects.Class.Classs;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Backend.Objects.School.School;
import app.classmanagementappclient.Backend.Objects.Student.Student;
import app.classmanagementappclient.Backend.Objects.Teacher.*;
import app.classmanagementappclient.Backend.Objects.Term.ClassTerm;
import app.classmanagementappclient.Backend.Objects.Year.ClassYear;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class UtilityClass
{  
    public Teacher getTeacher (PreparedStatement statement) throws SQLException {
        ResultSet set = statement.executeQuery();
        
        boolean isLast = set.last();
        if (!isLast)
            return null;
        
        ResultSetMetaData rsmd = set.getMetaData();
        Object[] teacherCredentials = new Object[5];
        
        for (int i = 1; i <= rsmd.getColumnCount(); i++)
            teacherCredentials[i-1] = set.getObject(i);
        
        Teacher teacher = new Teacher(Integer.parseInt( String.valueOf( teacherCredentials[0] ) ), String.valueOf( teacherCredentials[1] ), String.valueOf( teacherCredentials[4] ), String.valueOf( teacherCredentials[2] ),  String.valueOf( teacherCredentials[3]));
        return teacher;
    }
    
    public String getStudentName (Connection connection, String studentID) throws SQLException 
    {
        String query = "SELECT studentname FROM students WHERE studentid = ? AND studentteacher = ?";
        String studentName;
        
        try (PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            int index = setStringStatement(statement, studentID, 1);
            setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
            studentName = getStatementStringResult(statement, 1);
        }    
        return studentName;
    } 
    
    public String getClassName (Connection connection, int classID) throws SQLException
    {
        String query = "SELECT classname FROM classes WHERE classid = ?";
        return getAStringClassDetailInfo(connection, query, classID);
    }   
    
    // can be a lessonname or classname
    public String getAStringClassDetailInfo (Connection connection, String query, int classID) throws SQLException
    {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, classID, 1);
            return getStatementStringResult(statement, 1);
        }        
    }
    
    public int getClassNum (Connection connection, int schoolNum, String className) throws SQLException
    {
        String query = """
                        SELECT classNum FROM classData WHERE className = ? AND schoolNum = ?
                       """;  
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            int index = setStringStatement(statement, className, 1);
            setIntStatement(statement, schoolNum, index);
            return getStatementIntResult(statement, 1);
        }
    }
    
    public int getClassYearNum (Connection connection, int classNum, int year) throws SQLException
    {
        String query = """
                        SELECT classYearNum FROM classYearData WHERE year = ? AND classNum = ?
                       """;  
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            setIntsStatement(statement, new Integer[] { year, classNum }, 1);
            return getStatementIntResult(statement, 1);
        }
    }    
    
    public int getClassTermNum (Connection connection, int classYearNum, int term) throws SQLException
    {
        String query = """
                        SELECT classTermNum FROM classTermData WHERE term = ? AND classYearNum = ?
                       """;  
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            setIntsStatement(statement, new Integer[] { term, classYearNum }, 1);
            return getStatementIntResult(statement, 1);
        }
    }    

    public int getClassLessonNum (Connection connection, int classTermNum, String lessonName) throws SQLException
    {
        String query = """
                        SELECT classLessonNum FROM classLessonData WHERE lessonName = ? AND classTermNum = ?
                       """;  
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            int index = setStringStatement(statement, lessonName, 1);
            setIntStatement(statement, classTermNum, index);
            return getStatementIntResult(statement, 1);
        }
    }

    public int getStudentNum (Connection connection, Student student) throws SQLException
    {
        String query = """
                        SELECT studentNum FROM studentPersonalData WHERE studentID = ? AND TeacherNum = ?
                       """;  
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            int index = setStringStatement(statement, student.getStudentID(), 1);
            setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
            return getStatementIntResult(statement, 1);
        }        
    }
    
    public int getStudentClassNum (Connection connection, int studentNum, int classTermNum) throws SQLException
    {
        String query = """
                        SELECT studentClassNum FROM studentClassData WHERE studentNum = ? AND classTermNum = ?
                       """;  
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            setIntsStatement(statement, new Integer[] { studentNum, classTermNum }, 1);
            return getStatementIntResult(statement, 1);
        }        
    }    
    
    public int getClassID (Connection connection, String schoolName, String className, String lessonName, int term, int year, int teacherID) throws SQLException
    {
        String query = """
                        SELECT classes.classid FROM classes 
                        JOIN schools ON classes.classid = schools.classid
                        WHERE schools.schoolName = ? AND classes.classname = ? AND classes.lessonname = ? 
                        AND classes.term = ? AND classes.year = ? AND classes.teacherid = ?
                       """;
        try (PreparedStatement statement = connection.prepareStatement(query)) 
        {
            int index = setStringsStatement(statement, new String[]{ schoolName, className, lessonName}, 1);
            setIntsStatement(statement, new Integer[]{ term, year, teacherID}, index);
            return getStatementIntResult(statement, 1);
        }
    }
    
    public int getClassTerm (Connection connection, int classID) throws SQLException
    {
        String query = "SELECT term FROM classes WHERE classid = ?";
        return getAnIntClassDetailInfo (connection, query, classID);
    }
    
    public int getClassYear (Connection connection, int classID) throws SQLException
    {
        String query = "SELECT year FROM classes WHERE classid = ?";
        return getAnIntClassDetailInfo (connection, query, classID);
    }
    
    private int getAnIntClassDetailInfo (Connection connection, String query, int classID) throws SQLException
    {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, classID, 1);
            return getStatementIntResult(statement, 1);
        }          
    }
    
    public boolean checkTeacherCredentialsSimilarity (Connection connection, Teacher teacher)
    {
        boolean bool = true;
        try (PreparedStatement statement = connection.prepareStatement("SELECT teacherNum FROM teacherPersonalData WHERE teacherPhoneNumber = ?"))
        {
            setStringStatement(statement, teacher.getPhoneNumber(), 1);
            int res = getStatementIntResult(statement, 1);
            bool = res != Integer.MIN_VALUE;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
        return bool;
    }    
    
    public void sortClassByRank (ArrayList<ArrayList<StringBuilder>> aggregateAttendanceRecord)
    {
        aggregateAttendanceRecord.sort((row1, row2) -> 
        {    
            int index = row1.size() - 1;
            double rank1 = Double.parseDouble(row1.get(index).toString());
            double rank2 = Double.parseDouble(row2.get(index).toString());    
            return rank1 > rank2 ? -1 : 1;
        });
    }
    
    
    public String getWildCardsFormattedQuery (String query, int totalItems, String firstKeyword, String...lastKeyword) 
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(firstKeyword);

        for (int i = 0; i < totalItems; i ++) {
            String appendStr = (i == totalItems - 1) ? "?)" : "?,";
            buffer.append(appendStr);
        }
        return lastKeyword.length == 0 ? query + buffer : query + buffer + lastKeyword[0];  
    }
    
    public boolean pinCheck (String pin, Connection connection)
    {
        String query = "SELECT teacherid, pin FROM teachers WHERE teacherid = ? AND pin = ?";
        int teacherID = LoggedInTeacher.getLoggedInTeacher().getTeacherNum();
        boolean b;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) 
        {
            int index = setIntStatement(statement, teacherID, 1);
            setStringStatement(statement, pin, index);
            
            int resTeacherID = getStatementIntResult(statement, 1);
            String pinRes = getStatementStringResult(statement, 2);
            
            b =  (teacherID == resTeacherID) && (pin.equals(pinRes));
        }
        catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
        return b;
    }
    
    public LocalDate formatDate (String date) {
        date = date.split("(\s\\/\\d*)")[0];
        final String[] dateAttributes = date.split("-");
        final int day = Integer.parseInt(dateAttributes[0]);
        final int month = Integer.parseInt(dateAttributes[1]);
        final int year  = Integer.parseInt(dateAttributes[2]);
        return LocalDate.of(year, month, day);
    }    
    
    /**
     *The method can make any DQL SELECT operation.Still looking on how we can involve other languages too like DML
     * @param queryResults
     * @param tableName
     * @param argClms
     * @return
     */
    public String setDynamicDQ (ArrayList<String> queryResults, String tableName, String...argClms)
    {
        StringBuilder builder = new StringBuilder();
        String operation = "SELECT";
        
        builder.append(operation).append(" ");
        appendQueryResults(builder, queryResults);
        builder.append(tableName).append(" ");
        if (argClms.length != 0)
            appendArguments(builder, argClms);
        return builder.toString();
    }
    
    private void appendQueryResults (StringBuilder builder, ArrayList<String> queryResults) 
    {
        for (int i = 0; i < queryResults.size(); i++) 
        {
            String res = queryResults.get(i);
            if (i == queryResults.size() - 1)
                builder.append(res).append(" ");
            else
                builder.append(res).append(", ");
        }
    }
    
    private void appendArguments (StringBuilder builder, String[] arguments)
    {
        builder.append("WHERE ");
        for (int i = 0; i < arguments.length; i++) 
        {
            String arg = arguments[i];
            if (i == arguments.length - 1)
                builder.append(arg).append(" = ").append("?");
            else
                builder.append(arg).append(" = ").append("? ").append("AND ");
        }
    }

    public<T> int setIntsStatement (PreparedStatement statement, T[] values, int index) throws SQLException
    {
        for (T value : values) {
            index = setIntStatement(statement, (Integer)value, index);
        }        
        return index;        
    }  
    
    public<T> int setStringsStatement (PreparedStatement statement, T[] values, int index) throws SQLException
    {
        for (T value : values)
            index = setStringStatement(statement, (String)value, index);
               
        return index;        
    }    

    public<T> int setDatesStatement (PreparedStatement statement, T[] values, int index) throws SQLException
    {
        for (T value : values) {
            index = setDateStatement(statement, (java.sql.Date) value, index);
        }        
        return index;        
    }

    public<T> int setTimestampsStatement (PreparedStatement statement, T[] values, int index) throws SQLException
    {
        for (T value : values) {
            index = setTimestampStatement(statement, (Timestamp) value, index);
        }        
        return index;        
    }    
    
    public int setIntStatement (PreparedStatement statement, int value, int index) throws SQLException {
        statement.setInt(index, value); 
        return index + 1;
    }
    
    public int setDoubleStatement(PreparedStatement statement, double value, int index) throws SQLException {
        statement.setDouble(index, value);
        return index + 1;
    }    
    
    public int setStringStatement (PreparedStatement statement, String string, int index) throws SQLException {
        statement.setString(index, string);
        return index + 1;
    }
    
    public int setDateStatement (PreparedStatement statement, java.sql.Date date, int index) throws SQLException {
        statement.setDate(index, date);
        return index + 1;
    } 
    
    public int setTimestampStatement (PreparedStatement statement, Timestamp timestamp, int index) throws SQLException {
        statement.setTimestamp(index, timestamp);
        return index + 1;
    }     
    
    public int getSchoolNum (Connection connection, School school) throws SQLException
    {
        String query = "SELECT schoolNum FROM schoolData WHERE teacherNum = ? AND schoolName = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) 
        {
            int index = setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), 1);
            setStringStatement(statement, school.getSchoolName(), index);
            return getStatementIntResult(statement, 1);
        }
    }   
    
    //CREATE VIEW attendanceData AS SELECT attendanceNumlessonNum, attendanceDate, recordedAt FROM attendance;
    public int getLastlyInsertedAttendanceNum (Connection connection, Attendance attendance) throws SQLException
    {
        String query = "SELECT attendanceNum FROM attendanceData WHERE lessonNum = ? AND attendanceDate = ? ORDER BY attendanceDate DESC LIMIT 1";
        int attendanceNum = Integer.MIN_VALUE;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = setIntStatement(statement, attendance.getLessonNum(), 1);
            setDateStatement(statement, attendance.getDate(), index);
            attendanceNum = getStatementIntResult(statement, 1);
        }
        
        return attendanceNum;
    }
    
    public int getStudentNumByStudentClassNum (Connection connection, int studentClassNum) throws SQLException
    {
        String query = """
                        SELECT studentNum FROM studentClassData WHERE studentClassNum = ?
                       """;
        int studentNum = 0;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, studentClassNum, 1);
            studentNum = getStatementIntResult(statement, 1);
        }        
        return studentNum;
    }
    
    public Student getStudentPersonalData (Connection connection, int studentNum) throws SQLException
    {
        String query = "SELECT studentID, studentName FROM studentPersonalData WHERE studentNum = ?";
        
        List<List<Object>> objects = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, studentNum, 1);
            getObjectsStatementByRowRes(statement, objects);
        }        
        
        if (objects.isEmpty())
            return null;
                
        return new Student (String.valueOf(objects.get(0).get(0)),  String.valueOf(objects.get(0).get(1)));
    }
        
    public int getLessonNumByAttendanceNum (Connection connection, int attendanceNum) throws SQLException
    {
        String query = """
                        SELECT lessonNum FROM attendanceData WHERE attendanceNum = ?
                       """;
        int lessonNum = 0;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, attendanceNum, 1);
            lessonNum = getStatementIntResult(statement, 1);
        }        
        return lessonNum;
    }

    public void getAssessmentDetails (Connection connection, int assessmentNum, List<Object> assessmentDetails) throws SQLException
    {
        String query = """
                        SELECT LessonNum, AssessmentName, AssessmentDate, TotalMarks FROM assessmentData WHERE assessmentNum = ?
                       """;
        List<List<Object>> collections = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, assessmentNum, 1);
            getObjectsStatementByRowRes(statement, collections);
        }
        for (List<Object> collection : collections) {
            for (Object coll : collection)
                assessmentDetails.add(coll);
        }
    }    
    
    public String getLessonName (Connection connection, int lessonNum) throws SQLException
    {
        String query = """
                        SELECT lessonName FROM classLessonData WHERE classLessonNum = ?
                       """;
        String lessonName;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntStatement(statement, lessonNum, 1);
            lessonName = getStatementStringResult(statement, 1);
        }        
        return lessonName;
    }    
    
    public int getAttendanceNum (Connection connection, java.sql.Date date, Timestamp timestamp) {
        
        String query = """
                        SELECT attendanceNum FROM attendanceData WHERE attendanceDate = ? AND recordedAt = ?
                       """;
        int res = Integer.MIN_VALUE;
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            int index = setDateStatement(statement, date, 1);
            setTimestampStatement(statement, timestamp, index);
            res = getStatementIntResult(statement, 1);
        } catch (SQLException ex) {
            System.getLogger(UtilityClass.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return res;
    }
    
    public int getAttendanceRecord (Connection connection, AttendanceRecord attendanceRecord) throws SQLException 
    {
        String query = " SELECT COUNT(*) FROM studentAttendanceRecordData WHERE attendanceNum = ? AND studentClassNum = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query))  {
            setIntsStatement(statement, new Integer[] { attendanceRecord.getAttendanceNum(), attendanceRecord.getStudentClassNum() }, 1);
            return getStatementIntResult(statement, 1);
        } 
    }
    
    public int getAssessment (Connection connection, Assessment assessment) throws SQLException 
    {   
        String query = " SELECT COUNT(*) FROM assessmentData WHERE  LessonNum = ? AND AssessmentName = ? AND AssessmentDate = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query))  {
            int index = setIntStatement(statement, assessment.getLessonNum() ,1);
            index = setStringStatement(statement, assessment.getAssessmentName(), index);
            setDateStatement(statement, assessment.getAssessmentDate(), index);
            return getStatementIntResult(statement, 1);
        } 
    } 
    
    public int getAssessmentRecord (Connection connection, AssessmentRecord assessmentRecord) throws SQLException 
    {
        String query = " SELECT COUNT(*) FROM studentAssessmentRecordData WHERE AssessmentNum = ? AND StudentClassNum = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setIntsStatement(statement, new Integer[] { assessmentRecord.getAssessmentNum(), assessmentRecord.getStudentClassNum() }, 1);
            return getStatementIntResult(statement, 1);
        }         
    }
    
    public double getStatementDoubleResult (PreparedStatement statement, int index) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        double res = Double.MIN_VALUE;
        
        while (set.next()) {
            res = set.getDouble(index);
            if (set.wasNull()) {
                res = Double.MIN_VALUE;
            }
        }
        return res;
    }
    
    public void getClmDoubleStatementResult (PreparedStatement statement, List<Double> doubleCollection ,int index) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next())
            doubleCollection.add( set.getDouble(index) );
        
    }
    
    public void getRowDoubleStatementResult (PreparedStatement statement, List<List<Double>> doubleCollection) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        
        while (set.next()) 
        {
            List<Double> row = new ArrayList<>();
            boolean wasNull = true;
            
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                
                row.add (set.getDouble(i));
                
                if (!set.wasNull() && wasNull)
                    wasNull = false;
            }

            if (!wasNull)
                doubleCollection.add(row);
        }
    }    

    public int getStatementIntResult (PreparedStatement statement, int index) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        int res = Integer.MIN_VALUE;

        while (set.next()) {
            res = set.getInt(index);
            if (set.wasNull()) {
                res = Integer.MIN_VALUE;
            }
        }        
        return res;
    }
    
    public void getOneClmIntsStatementResult (PreparedStatement statement, List<Integer> intCollection ,int index)  throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next()) {
            int data = set.getInt(index);
            intCollection.add(data);
        }
    }
    
    public String getStatementStringResult (PreparedStatement statement, int index) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        String res = null;
        
        while (set.next()) {
            res = set.getString(index);
            if (set.wasNull()) {
                return null;
            }
        }
        return res;
    }
    
    public void getStringsStatementByClmRes (PreparedStatement statement, int index, List<String> collection) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next())
            collection.add (set.getString(index));
    }
    
    public void getStringsStatementByRowRes (PreparedStatement statement, List<List<String>> collections) throws SQLException {
        
        ResultSet set = statement.executeQuery();
        
        if (set.isLast())
            return;        
        
        ResultSetMetaData rsmd = set.getMetaData();
        
        while (set.next()) 
        {
            List<String> collection = new ArrayList<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                
                String string = set.getString(i);
                if (string == null || string.isBlank())
                    break;
                
                collection.add(string);
            }
            collections.add(collection);
        }
    }

    public void getObjectsStatementByRowRes (PreparedStatement statement, List<List<Object>> collections) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        ResultSetMetaData rsmd = set.getMetaData();
        
        if (set.isLast())
            return;
        
        while (set.next()) 
        {
            List<Object> collection = new ArrayList<>();
            
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
                collection.add(set.getObject(i));
                    
            collections.add(collection);
        }
    }
    
    public void getSchoolsRowRes (PreparedStatement statement, List<School> schools) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next()) {
            School school = new School(set.getString(2));
            school.setSchoolNum(set.getInt(1));
            schools.add(school);
            if (set.wasNull()) 
                schools.clear();
        }
    }
    
    public void getClassesRowRes (PreparedStatement statement, List<Classs> classes) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next()) {
            Classs classs = new Classs(set.getString(2));
            classs.setClassNum(set.getInt(1));
            classs.setSchoolNum(set.getInt(3));
            classes.add(classs);
            if (set.wasNull()) 
                classes.clear();
        }
    }
    
    public void getClassYearsRowRes (PreparedStatement statement, List<ClassYear> years) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next()) {
            ClassYear year = new ClassYear(set.getInt(2));
            year.setClassYearNum(set.getInt(1));
            year.setClassNum(set.getInt(3));
            years.add(year);
            if (set.wasNull()) 
                years.clear();
        }
    }

    public void getClassTermRowRes (PreparedStatement statement, List<ClassTerm> terms) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next()) {
            ClassTerm term = new ClassTerm(set.getInt(2));
            term.setClassTermNum(set.getInt(1));
            term.setClassYearNum(set.getInt(3));
            terms.add(term);
            if (set.wasNull()) 
                terms.clear();
        }
    }    

    public void getClassLessonRowRes (PreparedStatement statement, List<ClassLesson> lessons) throws SQLException 
    {
        ResultSet set = statement.executeQuery();
        
        while (set.next()) {
            ClassLesson lesson = new ClassLesson(set.getString(2));
            lesson.setClassLessonNum(set.getInt(1));
            lesson.setClassTermNum(set.getInt(3));
            lessons.add(lesson);
            if (set.wasNull()) 
                lessons.clear();
        }
    }  
    
    public void getOneRowObjectsStatementResults (PreparedStatement statement, List<Object> objects) throws SQLException
    {
        ResultSet set = statement.executeQuery();
        
        if (set.isLast())
            return;
        
        ResultSetMetaData metaData = set.getMetaData();
        int clms = metaData.getColumnCount();
        
        while (set.next()) {
            for (int i = 1; i <= clms; i++) {
                Object get = set.getObject(i);
                objects.add(get);
            }
        }
    }
    
    public void startTransaction (Connection connection) throws SQLException {
        String query = "START TRANSACTION";   
        PreparedStatement statement = connection.prepareStatement(query);
        executeStatement(statement);
    }
    
    public void setGapLock (Connection connection, String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        executeStatement(statement);
    } 

    public void commit (Connection connection) throws SQLException {
        String query = "COMMIT";
        PreparedStatement statement = connection.prepareStatement(query);
        executeStatement(statement);
    }
    
    private List<String> rowIterateController (ResultSet set) throws SQLException {
        List<String> collection = new ArrayList<>();
        strRowIteration(set, collection);
        return collection;
    }
    
    private void strRowIteration (ResultSet set, List<String> collection) throws SQLException 
    {
        ResultSetMetaData rsmd = set.getMetaData();
        
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            collection.add(set.getString(i));
        } 
    }
    
    public boolean executeDMLStament (PreparedStatement statement) throws SQLException {
        return statement.executeUpdate() > 0;
    }
    
    public void executeStatement (PreparedStatement statement) throws SQLException {
        statement.execute();
    }
    
    public void threadSleep () {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException ex) {
            System.getLogger(UtilityClass.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
