package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents;

import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.ClassExistanceChecker;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewClassData.ClassExistanceCheck.Proxies.MsgProxy1;
import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.MediumConnector.StudentClassInsertMediumConnector;
import app.classmanagementappclient.Frontend.InputWindows.InputWindow;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public abstract class StudentInsertParent 
{
    private final InputWindow inputWindow;

    public StudentInsertParent(InputWindow inputWindow) {
        this.inputWindow = inputWindow;
    }
    
    public StudentInsertParent() {
        this(null);
    }    
    
    protected void setTableDisplayTitle (TableDisplay tableDisplay) {
        
        tableDisplay.getTitleLabel().setText("New students to be saved to: " + 
                inputWindow.getRecordedSchoolNewStudentComboBox().getSelectedItem() + "/" + inputWindow.getRecordedClassNewStudentComboBox().getSelectedItem() + "/"+
                inputWindow.getRecordedYearNewStudentComboBox().getSelectedItem() + "/" + inputWindow.getRecordedTermsNewStudentComboBox().getSelectedItem());
    }    
    
    protected int getClassTermNum () {   
        ClassExistanceChecker checker = ClassExistanceChecker.getClassExistanceChecker(inputWindow);
        checker.checkClassExistance(new MsgProxy1());
        return checker.getClassTermNum();
    }
    
    public void submitData (List<List<String>> allData, Table table, int[] indexToCheck) {
        
        new SwingWorker<Object, Integer> () {
        
            private boolean isProcessed;
            private final StringBuilder errReport = new StringBuilder();
            
            @Override
            protected Object doInBackground() throws Exception {
          
                int classTermNum = getClassTermNum();

                if (classTermNum == Integer.MIN_VALUE)
                    return null;

                if (allData.isEmpty())
                    return null;

                int i = 0;

                for (List<String> data : allData) {

                    var connector = new StudentClassInsertMediumConnector(classTermNum, data.get( indexToCheck[0] ), data.get( indexToCheck[1] ));
                    boolean isInserted = connector.insert();

                    if (!isInserted) {
                        errReport.append(connector.getMsg()).append("\n\n");
                        i += 1;
                    }
                    else 
                        publish(i);
                }
                isProcessed = true;
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int chunk : chunks)
                    table.removeTableRow(table.getjTable().convertRowIndexToModel(chunk));
            }

            @Override
            protected void done() {
                
                if (!isProcessed)
                    return;
                
                if (allData.isEmpty()) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("No Data Found","Error Message", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                showReport(errReport.toString());
            }
        }.execute();       
    }

    protected void showReport (String msg) {
        new ShowReport().showReport(msg, getMsg());
    }
    
    protected String getMsg () {
        return "All students are inserted in the class successfully";
    }
    
    public InputWindow getInputWindow() {
        return inputWindow;
    }
}
