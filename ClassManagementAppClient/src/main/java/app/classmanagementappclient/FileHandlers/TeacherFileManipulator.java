package app.classmanagementappclient.FileHandlers;

import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.Main;

import  java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class TeacherFileManipulator 
{ 
    public static void storeTeacherIfLoggedIn () 
    {
       boolean isLoggedIn = isTeacherLoggedIn ();
        
        if (isLoggedIn) {
            Path teacherFilePath = Paths.get(Main.getApplicationFilesPaths().getProperty("LoggedInTeacherObject"));
            store (teacherFilePath);
        }
    }
    
    private static boolean isTeacherLoggedIn ()
    {
       Properties properties = LoggedInTeacher.getProperties();
       return Boolean.parseBoolean(properties.getProperty("isLoggedIn")); 
    }
    
    private static void store (Path teacherFilePath)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        
        try 
        {
            fileOutputStream = new FileOutputStream(teacherFilePath.toFile().getAbsoluteFile());
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            writeObject(objectOutputStream);
        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
        finally {
            closeOutputStreams(fileOutputStream, objectOutputStream);
        }
    }
    
    private static void writeObject (ObjectOutputStream objectOutputStream) throws IOException
    {
        LoggedInTeacher teacher = LoggedInTeacher.getLoggedInTeacher();
        objectOutputStream.writeObject(teacher);
    }
    
    private static void closeOutputStreams (FileOutputStream fileOutputStream, ObjectOutputStream objectOutputStream)
    {
        try {
            fileOutputStream.close();
            objectOutputStream.close();
        }
        catch (IOException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
    
    
    public static void loadTeacherIfLoggedIn () 
    {
       boolean isLoggedIn = isTeacherLoggedIn();
        
        if (isLoggedIn) 
        {
            Path teacherFilePath = Paths.get (Main.getApplicationFilesPaths().getProperty("LoggedInTeacherObject"));
            load(teacherFilePath);
        }
    }
    
    private static void load (Path teacherFilePath)
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        
        try 
        {
            fileInputStream = new FileInputStream(teacherFilePath.toFile());
            objectInputStream = new ObjectInputStream(fileInputStream);
            readObject(objectInputStream);
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
        finally {
            closeInputputStreams(fileInputStream, objectInputStream);
        }
    }
    
    private static void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException
    {
        LoggedInTeacher teacher = (LoggedInTeacher) objectInputStream.readObject();
        LoggedInTeacher.setLoggedInTeacher(teacher);
    }
    
    private static void closeInputputStreams(FileInputStream fileInputStream, ObjectInputStream objectInputStream)
    {
        try {
            fileInputStream.close();
            objectInputStream.close();
        } 
        catch (IOException ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
}
