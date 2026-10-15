/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.Dialog;

import app.classmanagementappclient.Frontend.DashboardView.Schedule.NewSchedule.ScheduleCenterPanel;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJDialog.ADialog;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

/**
 *
 * @author BEN
 */
abstract public class ScheduleDialog extends ADialog {

    private final ScheduleCenterPanel centerPanel = new ScheduleCenterPanel();
    private final Schedule schedule;

    public ScheduleDialog(Schedule schedule) {
        this.schedule = schedule;
    }
    
    @Override
    public void drawJDialog() {
        
        setWindowTitle("Schedule Window");
        
        super.setLocationPoint(new Point(550, 100));
        super.drawJDialog();
        
        addDialogListener();
    }

    @Override
    protected void handleSubmitEvent() {
        
        String title = getTitle();
        String description = getDescription();
        Date date = getDate();
        
        if (title.isBlank() || description.isBlank() || date == null) {
            super.showErrorMsg();
            return;
        }
        super.finalTouch(); // this call super ADialog finalTouch method, that method will call this.handler(), where this is the sub class of this.ScheduleDialog
    }    
    
    private void addDialogListener () {
        
        super.getDialog().addWindowListener(new WindowAdapter () {
            @Override
            public void windowClosed(WindowEvent e) {
                centerPanel.getTaskDate().cleanup();
            }
        });
    }
    
    public ScheduleCenterPanel getCenterPanel() {
        return centerPanel;
    }
    
    protected String getTitle () {
        return centerPanel.getTaskTitle().getText();
    }
    
    protected String getDescription () {
        return centerPanel.getTaskDescription().getText();
    }

    protected java.sql.Date getDate () {
        return FrontEndUtility.getDate(centerPanel.getTaskDate());
    }   

    public Schedule getSchedule() {
        return schedule;
    }
}
