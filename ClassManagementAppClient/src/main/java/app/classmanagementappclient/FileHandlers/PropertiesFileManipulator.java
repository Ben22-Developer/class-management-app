package app.classmanagementappclient.FileHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class PropertiesFileManipulator 
{   
    public static void propertyLoad (String path, Properties properties)
    {
        File f = new File(path);
        
        try (FileInputStream fileInputStream  = new FileInputStream(f))
        {
            properties.load(fileInputStream);
        } 
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException();
        } 
    }
    
    public static void propertyStore (String path, Properties properties)
    {
        File f = new File(path);
        
        try (FileOutputStream fos = new FileOutputStream(f)) {
            properties.store(fos, "Updated");    
        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }
}
