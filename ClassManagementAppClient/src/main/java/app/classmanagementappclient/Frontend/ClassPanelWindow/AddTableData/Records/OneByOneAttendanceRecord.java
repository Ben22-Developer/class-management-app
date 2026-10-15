package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1Int;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Main;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 * @author BEN
 */
public class OneByOneAttendanceRecord extends InsertOneByOneRecord{
    
    public OneByOneAttendanceRecord(DataEntry1Int proxy, int rowNbr, int attendanceNum) {
        super (proxy, rowNbr, attendanceNum);
    }
    
    @Override
    public void drawJDialog() {
        
        super.setHeaders(List.of("Student ID"));
        super.setValues(List.of(""));
        super.setTitleLable("Add Record");
        super.setWindowTitle("Attendance Record");
        
        super.drawJDialog();
    }

    @Override
    protected synchronized void finalTouch() {
        
        if (!handler())
            return;
        
        super.getProxy().setNextRecord(false);
        notifySleepers();
        
        UIUpdate(); 
    }
    
    @Override
    public boolean handler() {
        
        RecordLogic recordLogic = new RecordLogic();
        boolean res = recordLogic.attendanceRecord(super.getUserInputs().get(0), super.getNum());
        
        super.getUserInputs().clear();
        
        int studentClassNum = recordLogic.getStudentClassNum();
        
        if (res)
            setProxyObject(studentClassNum);
            
        return res;
    }
    
    private void setProxyObject (int studentClassNum) {
        
        var details = new StudentClassMedium().getStudentetailsByStudentClassNum(studentClassNum); // getting student id & student name
        int rowNbr = super.getRowNbr();
        rowNbr += 1;
        super.getProxy().setNewRecords(List.of(false, rowNbr, details.get(0), details.get(1)).toArray());
        super.setRowNbr(rowNbr);
        playAudio();
        succesMsg(details);
    }
    
    private void playAudio () {
        
        if (!canPlayAudio())
            return;
        
        new SwingWorker<Object, Object> () {
            @Override
            protected Object doInBackground() throws Exception {
                
                try (FileInputStream fis = new FileInputStream(Main.getApplicationFilesPaths().get("AttendanceSoundEffect").toString())) {
                    Player player = new Player(fis);
                    player.play();
                }
                catch(Exception ex) {
                    System.getLogger(OneByOneAttendanceRecord.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                return null;
            }
        }.execute();
    }
    
    private boolean canPlayAudio () {
        return FrontEndUtility.canPlayAudioFile("AttendanceAudioPlay");
    }
    
    private void succesMsg (List<String> details) {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Attendance recorded for:\nStudent ID: " + details.get(0) +"\nStudent Name: "+details.get(1), "Informational message", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
