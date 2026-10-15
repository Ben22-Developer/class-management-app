/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.NewSchedule;

import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AddNewScheduleUtility {
    
    private final Schedule schedule;
//    private final AddNewScheduleDialog addNewScheduleDialog = new AddNewScheduleDialog();
    
    public AddNewScheduleUtility(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public void run () {
        new SwingWorker<Object, Object> () {
            
            @Override
            protected Object doInBackground() throws Exception {
                
                
                
                return null;
            }

            @Override
            protected void process(List<Object> chunks) {
                super.process(chunks); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
        
            
        };
    }
    
    private void createTable () {
        
        
    }
    
    
}
