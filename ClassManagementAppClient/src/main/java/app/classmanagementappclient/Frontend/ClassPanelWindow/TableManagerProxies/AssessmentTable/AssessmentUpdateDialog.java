package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentTable;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Backend.Objects.Assessment.Assessment;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.TableDateData.DateChooserDialog;
import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AssessmentUpdateDialog  extends DateChooserDialog {
    
    private List<String> data;
    private final Assessment currAssessmentDetails;
    
    public AssessmentUpdateDialog(int assessmentNum, int lessonNum, String assessmentName, String assessmentDate, String totalMarks) {
        this.currAssessmentDetails = new Assessment(lessonNum, assessmentName, FrontEndUtility.getAppFormatDateToSQLFormatDate(assessmentDate), Integer.parseInt(totalMarks));
        this.currAssessmentDetails.setAssessmentNum(assessmentNum);
    }
    
    @Override
    public void drawJDialog() {
        setWindowTitle("Edit Assessment");
        setTitleLable("Edit");
        setLocationPoint(new Point (600, 150));
        super.drawJDialog();
    }
    
    @Override
    protected void addCenterPanel() {
        
        JDialog dialog = getDialog();
        JPanel centerPanel = new JPanel(new BorderLayout());
        var helper = new AssessmentDialogHepler();
        
        JPanel panel = helper.getPanel(this,"Assessment Name:", currAssessmentDetails.getAssessmentName());
        JPanel panel1 = helper.getPanel("Set Date:", super.getDateChooser(), currAssessmentDetails.getAssessmentDate());
        JPanel panel2 = helper.getPanel(this,"Total Marks:",String.valueOf(currAssessmentDetails.getTotalMarks()) );
        
        centerPanel.add(panel, BorderLayout.NORTH);
        centerPanel.add(panel1, BorderLayout.CENTER);
        centerPanel.add(panel2, BorderLayout.SOUTH);
        
        dialog.add(centerPanel, BorderLayout.CENTER);
    }
    
    
    public boolean submitHandler () {
        
        var userInputs = super.getUserInputs();
        
        setData(userInputs);
        
        boolean res = new AssessmentMedium().updateAssessment(currAssessmentDetails.getLessonNum(), userInputs.get(0), super.getDate(), userInputs.get(1), currAssessmentDetails);
        
        if (!res)
            return false;
        
        data.add(1, FrontEndUtility.getAppFormatDate(super.getDate()));
        super.setSubmitted(true);
        notifySleepers();
        return true;
    }
    
    @Override
    public synchronized void notifySleepers () {
        notifyAll();
    }

    public void setData(List<String> data) {
        this.data = data;
    }
    
    public synchronized List<String> getData () {
        
        while (!super.isSubmitted()) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        System.out.println("end");
        return data;
    }    
    
}