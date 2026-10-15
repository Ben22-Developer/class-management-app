/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.Dialog.Descendants;

import app.classmanagementappclient.Backend.Objects.CustomGson.LocalDateAdapter;
import app.classmanagementappclient.Backend.Objects.WebObject.Task.Task;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Dialog.ScheduleDialog;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.NewSchedule.ScheduleCenterPanel;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.ScheduleUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AddNewScheduleDialog extends ScheduleDialog {

    public AddNewScheduleDialog(Schedule schedule) {
        super(schedule);
    }
    
    @Override
    public void drawJDialog() {
        setTitleLable("Add New Task");
        super.drawJDialog();
    }

    @Override
    protected void addCenterPanel() {
        
        ScheduleCenterPanel panel = super.getCenterPanel();
        getDialog().add(panel, BorderLayout.CENTER);
    }


    @Override
    public boolean handler() {
        
        String title = super.getTitle();
        String description = super.getDescription();
        java.sql.Date date = super.getDate();
        
        var task = new Task(title, description, date.toLocalDate());
        
        var sw = new SwingWorker<Boolean, Task > () {
            
            @Override
            protected Boolean doInBackground() throws Exception {
                
                serverPost();
                
                
                publish(task);
                return true;
            }

            @Override
            protected void process(List<Task> chunks) {
                
                Schedule s = getSchedule();
                Task task = chunks.getFirst();
                ScheduleUtility.addNewTaskSchedule(s, s.getScheduleTab(), s.getScheduleTable(), task, s.getScheduleTable().getjTable().getRowCount());
            }
            
            
            
           private void serverPost () { // throw to terminate it
               
               Gson gson = new GsonBuilder()
                       .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                       .create();
               
               var request = HttpRequest
                       .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + CommonWebServiceFeatures.postSchedule))
                       .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                       .header("Content-Type", "application/json")
                       .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                       .build();
               
               var client = CommonWebServiceFeatures
                       .getHttpResponse(request, getSchedule().getConnectionLabel());
               
               if (client.statusCode() > 299) {
                   new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(client.body().toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
                   throw new RuntimeException();
               }
                   
           }
        };
        
        try {
            sw.execute();
            return (boolean) sw.get();
        }
        catch (Exception e) {}
        return false;
    }
}
