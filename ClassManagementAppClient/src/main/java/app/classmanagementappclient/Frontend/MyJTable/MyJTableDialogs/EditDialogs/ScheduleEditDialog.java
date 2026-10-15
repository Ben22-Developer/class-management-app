/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.EditDialogs;

import app.classmanagementappclient.Frontend.DashboardView.Schedule.NewSchedule.ScheduleCenterPanel;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.TableEditDialog;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ScheduleEditDialog extends TableEditDialog{

    private final ScheduleCenterPanel scheduleCenterPanel = new ScheduleCenterPanel();
    
    @Override
    public void drawJDialog() {
        
        setWindowTitle("Update Schedule");
        super.setLocationPoint(new Point(550, 100));
        super.drawJDialog();
    }

    @Override
    protected void addCenterPanel() {
        
        JDialog dialog = getDialog();
        dialog.add(scheduleCenterPanel, BorderLayout.CENTER);
        populateCurrentDataToUI();
    }
    
    private void populateCurrentDataToUI () {
        
        List<String> rowData = getSelectedRowData();
        scheduleCenterPanel.getTaskTitle().setText(rowData.getFirst());
        scheduleCenterPanel.getTaskDescription().setText(rowData.get(1));
        scheduleCenterPanel.getTaskDate().setDate(FrontEndUtility.getAppFormatDateToSQLFormatDate(rowData.getLast()));
        scheduleCenterPanel.getTaskDate().setMinSelectableDate(java.util.Date.from(Instant.now()));
    }

    @Override
    protected void handleSubmitEvent() {
        
        String title = scheduleCenterPanel.getTaskTitle().getText();
        String description = scheduleCenterPanel.getTaskDescription().getText();
        
        Instant instant = scheduleCenterPanel.getTaskDate().getDate().toInstant();
        String date = FrontEndUtility.getAppFormatDate(LocalDate.ofInstant(instant, ZoneId.systemDefault()));
        
        List<String> userInputs = getUserInputs();
        
        userInputs.add(title);
        userInputs.add(description);
        userInputs.add(date);
        
        if (title.isBlank() || description.isBlank() || date == null) {
            showErrorMsg();
            return;
        }
        
        if (!handler()) 
            return;
        
        JDialog dialog = getDialog();
        dialog.dispose();
    }
    
    
}
