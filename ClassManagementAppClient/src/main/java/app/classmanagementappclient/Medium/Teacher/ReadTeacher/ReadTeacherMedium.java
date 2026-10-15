package app.classmanagementappclient.Medium.Teacher.ReadTeacher;

import app.classmanagementappclient.Backend.Database.Read.ReadTeacherData.*;
import app.classmanagementappclient.Backend.Database.Read.Readables;
import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import app.classmanagementappclient.Medium.MediumUtility;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadTeacherMedium {
    
    public List<String> getUserData () 
    {
        ReadTeacherPersonalData rtpd = null;
        
        try (Connection connection = MediumUtility.getConnection()) {
            rtpd = new ReadTeacherPersonalData();
            setCommonFeatures(rtpd, connection);
        } catch (SQLException ex) {
            System.getLogger(ReadTeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return rtpd.getData();
    }
    
    public Teacher getTeacherByUsername (String username)
    {
        ReadTeacherByUsername readTeacherByUsername = null;
        
        try (Connection connection = MediumUtility.getConnection()) 
        {
            readTeacherByUsername = new ReadTeacherByUsername(username);
            setCommonFeatures(readTeacherByUsername, connection);
        } 
        catch (SQLException ex) 
        {
            System.getLogger(ReadTeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return readTeacherByUsername.getData();
    }
    
    public String getTeacherPin () 
    {
        ReadTeacherPin rtp = null;
        
        try (Connection connection = MediumUtility.getConnection()) {
            rtp = new ReadTeacherPin();
            setCommonFeatures(rtp, connection);
        } catch (SQLException ex) {
            System.getLogger(ReadTeacherMedium.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return rtp.getPin();
    } 
    
    private void setCommonFeatures (Readables readables, Connection connection) throws SQLException
    {
        readables.setConnection(connection);
        readables.executeRead();
    }
}
