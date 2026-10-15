/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.Load;

import app.classmanagementappclient.Backend.Objects.WebObject.Task.Task;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public interface Schedule_ArchivePanelLoadContract {
    
    void loadSchedule (Schedule schedule);
    
    boolean apiKeyCheckUp();
    
    void showNoTaskPanel (Schedule schedule, JPanel tabPanel);
    
    void showTasks (Schedule schedule, List<Task> tasks);
    
    void removeCenterPanelComponent (JPanel scheduleTab);
    
    void removeSouthPanelComponent (JPanel scheduleTab);
    
    void addCenterPanelComponent (JPanel scheduleTab, Component component);
    
    void addSouthPanelComponent (JPanel scheduleTab, Component component);
    
    void populateSouthPanel (Schedule s);
    
    void populateCenterPanel (JScrollPane scrollPane, Table scheduleTable)    ;
    
    List<Task> loadTasks (Schedule s);
    
    void addSouthComponentsListeners (Schedule s);
    
    void reload(Schedule s);
}
