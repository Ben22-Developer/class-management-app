package app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.Records;

import app.classmanagementappclient.Medium.StudentClass.StudentClassMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.SubClasses.Collections.DataEntry1Int;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class OneByOneAssessmentRecord extends InsertOneByOneRecord{
    
    public OneByOneAssessmentRecord (DataEntry1Int proxy, int rowNbr, int assessmentNum) {
        super (proxy, rowNbr, assessmentNum);
    }
    
    @Override
    public void drawJDialog() {
        
        super.setHeaders(List.of("Student ID", "Student Marks"));
        super.setValues(List.of("", ""));
        super.setTitleLable("Add Record");
        super.setWindowTitle("Assessment Record");
        
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
        var userInputs = super.getUserInputs();
        boolean res = recordLogic.assessmentRecord(userInputs.get(0), userInputs.get(1), super.getNum());
        
        int studentClassNum = recordLogic.getStudentClassNum();
        
        if (res)
            setProxyObject(studentClassNum, userInputs.get(1));
        
        super.getUserInputs().clear();
        
        return res;
    }
    
    private void setProxyObject (int studentClassNum, String studentMarks) {
        
        var details = new StudentClassMedium().getStudentetailsByStudentClassNum(studentClassNum); // getting student id & student name
        int rowNbr = super.getRowNbr();
        rowNbr += 1;
        super.getProxy().setNewRecords(List.of(false, rowNbr, details.get(0), details.get(1), studentMarks, "Edit").toArray());
        super.setRowNbr(rowNbr);
        succesMsg(details);
    }
    
    private void succesMsg (List<String> details) {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("Assessment recorded for:\nStudent ID: " + details.get(0) +"\nStudent Name: "+details.get(1), "Informational message", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
