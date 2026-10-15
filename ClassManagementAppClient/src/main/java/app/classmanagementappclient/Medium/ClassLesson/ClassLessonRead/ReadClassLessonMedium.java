package app.classmanagementappclient.Medium.ClassLesson.ClassLessonRead;


import app.classmanagementappclient.Medium.MediumUtility;
import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadDataInterface;
import app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Name.*;
import app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Num.ReadOneClassLessonNumProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Num.ReadOneClassLessonNumProxy1;
import app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Num.ReadOneTermLessonsNums;
import app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.Object.ReadClassLessonsObjectProxy;
import app.classmanagementappclient.Backend.Database.Read.ReadClassLessonData.ReadClassLessonData;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadClassLessonMedium {
    
    public int getOneClassLessonNum (String schoolName, String className, int classYear, int classTerm, String lessonName) {
        
        ReadOneClassLessonNumProxy proxy = new ReadOneClassLessonNumProxy(schoolName, className, classYear, classTerm, lessonName);
        executeReadOperation(proxy);
        return proxy.getData().isEmpty() ? Integer.MIN_VALUE : proxy.getData().get(0);
    }
    
    public int getOneClassLessonNum (int classTermNum, String lessonName) {
        
        ReadOneClassLessonNumProxy1 proxy = new ReadOneClassLessonNumProxy1(classTermNum, lessonName);
        executeReadOperation(proxy);
        return proxy.getData().isEmpty() ? Integer.MIN_VALUE : proxy.getData().get(0);
    }     
            
        
    public List<String> getAllLessons () {

        ReadAllClassLessonsProxy proxy = new ReadAllClassLessonsProxy();
        executeReadOperation(proxy);
        return proxy.getData();
    }       
        
    public List<List<Object>> getAllLessonsWithDetails () {

        ReadAllClassLessonsProjects proxy = new ReadAllClassLessonsProjects();
        executeReadOperation(proxy);
        return proxy.getData();
    }               

    public List<String> getAllLessonsInTerm (String schoolName, String className, int classYear, int classTerm) {
        ReadClassLessonsInTermProxy proxy = new ReadClassLessonsInTermProxy(schoolName, className, classYear, classTerm);
        executeReadOperation(proxy);
        return proxy.getData();
    }
        
    public List<String> getAllLessonsInTerm (int classTermNum) {
        
        ReadClassLessonsInTermProxy1 proxy = new ReadClassLessonsInTermProxy1(classTermNum);
        executeReadOperation(proxy);
        return proxy.getData();
    } 
    
    public List<List<Object>> getAllLessonsInTerm (int classTermNum, List<String> lessonNames) {
        
        ReadOneTermLessonsNums proxy = new ReadOneTermLessonsNums(classTermNum, lessonNames);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    public String getLessonName (int classLessonNum) {   
        var proxy = new ReadClassLessonNameProxy(classLessonNum);
        executeReadOperation(proxy);
        return proxy.getData().isEmpty() ? "" : proxy.getData().get(0);
    }
    
    public List<ClassLesson> getClassLessonsInTerm (List<Integer> classTermNums) {
        var proxy = new ReadClassLessonsObjectProxy(classTermNums);
        executeReadOperation(proxy);
        return proxy.getData();
    }
    
    private void executeReadOperation (ReadDataInterface proxy) {
        
        try (Connection connection = MediumUtility.getConnection()) {        
            ReadClassLessonData classLessonData = new ReadClassLessonData(proxy);
            classLessonData.setConnection(connection);
            classLessonData.executeRead();
        }catch (SQLException ex) {
                System.getLogger(ReadClassLessonMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }    
        
}
