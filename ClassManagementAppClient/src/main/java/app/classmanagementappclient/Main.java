/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package app.classmanagementappclient;

import app.classmanagementappclient.AppRun.AppRun;
import app.classmanagementappclient.Backend.Database.DatabaseManipulation;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import app.classmanagementappclient.FileHandlers.PropertiesFileManipulator;
import app.classmanagementappclient.FileHandlers.TeacherFileManipulator;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;

import java.util.Properties;

/**
 *
 * @author BEN
 */
public class Main {

    private static final Properties applicationFilesPaths = new Properties();
    
    public static void main(String[] args){
        loadApp();
        runApp();
    }
    
//    public static void programRun ()
//    {
//        Properties runAppOrTest = new Properties();
//        String path = (String) applicationFilesPaths.get("RunAppOrTestProperty");
//        PropertiesFileManipulator.propertyLoad(path, runAppOrTest);
//
//        if (Boolean.parseBoolean( runAppOrTest.getProperty("RunTest")))
//            runTests();
//        else
//            runApp();
//    }
    
    private static void loadApp ()
    {
        setApplicationFilesPaths();
        loadProperties();
        loadObjects();
//        LoggedInTeacher.makeTeacherLoggedOut();
//        new TestingDataUtilityClass().loginNewTeacherinApp(11);
        
        System.out.println("Teacher in the system: " + LoggedInTeacher.getLoggedInTeacher());
    }
    
    private static void loadProperties ()
    {
        DatabaseManipulation.setProperties();
        LoggedInTeacher.setProperties();
        CommonWebServiceFeatures.loadWebProperties();
    }
    
    private static void loadObjects () {
        TeacherFileManipulator.loadTeacherIfLoggedIn();
    }

    public static void setApplicationFilesPaths () {
        PropertiesFileManipulator.propertyLoad("Application Files/Properties/ApplicationFilesPath.properties", applicationFilesPaths);
    }

    public static Properties getApplicationFilesPaths() {
        return applicationFilesPaths;
    }
    
    private static void runTests () {
        System.out.println("Tests needs to be revised they can be found at the root folder.");
    }
    
    private static void runApp () {
        AppRun.startApp();
    }
}
