package app.classmanagementappclient.Frontend.SavedProjectsWindow.SubClasses.SavedStudentsUtility.DisplayStudentClasses;

import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
abstract public class DataDisplay {
    
    private final List<Table> classTables = new ArrayList<>();
    private List<List<String>> studentDetails;
    private int currIndex;
    private final Table table;
    private String msg;
    private final int checkLimit = 5;
    
    public DataDisplay(Table table) {
        this.table = table;
    }
    
    public void run () {
        
        if (table.getCheckedRows().size() > checkLimit) {
            checkOverLimit();
            return;
        }
        
        if (table.getCheckedRows().isEmpty()) {
            noCheckedRows();
            return;
        }        
        
        setStudentIDs();
        collectData();
        populateDataToUI();
    }
    
    private void checkOverLimit () {
        setMsg("Check at most 5 students\nYou have checked " + table.getCheckedRows().size() + " students");
        showMsg();
    }
    
    private void noCheckedRows () {
        setMsg("There's no checked students to display");
        showMsg();
    }    

    private void showMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(getMsg(), "Error Message", JOptionPane.ERROR_MESSAGE);
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Table> getClassTables() {
        return classTables;
    }

    public void setStudentDetails(List<List<String>> studentDetails) {
        this.studentDetails = studentDetails;
    }
    
    public List<List<String>> getStudentDetails() {
        return studentDetails;
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public void setCurrIndex(int currIndex) {
        this.currIndex = currIndex;
    }

    public Table getTable() {
        return table;
    }
    
    public abstract void setStudentIDs();
    public abstract void collectData();
    public abstract void populateDataToUI();    
}
