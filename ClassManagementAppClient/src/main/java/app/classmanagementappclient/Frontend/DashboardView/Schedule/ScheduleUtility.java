/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule;

import app.classmanagementappclient.Backend.Objects.CustomGson.LocalDateAdapter;
import app.classmanagementappclient.Backend.Objects.WebObject.Task.Task;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.DeleteSchedule.DeleteSchedule;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Load.ArchivePanelLoad;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Load.SchedulePanelLoad;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Load.Schedule_ArchivePanelLoadContract;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ScheduleTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ScheduleUtility {
    
    
    public static Schedule createSchedule (JLabel connectionLabel) {
        
        var schedule = new Schedule(connectionLabel);
        new SchedulePanelLoad().loadSchedule(schedule);
        new ArchivePanelLoad().loadSchedule(schedule);
        showReloadAllBtn(schedule);
        
        return schedule;
    }
    
    private static void showReloadAllBtn (Schedule s) {
        
        new SwingWorker<Object, JButton> () 
        {
            private final JPanel reloadPanel = s.getReloadPanel();
            
            @Override
            protected Object doInBackground() throws Exception {
                
                JButton reloadButton = s.getSouthPanelUIElts().getNewReloadBtn();
                
                s.setReloadAllBtn(reloadButton);
                
                reloadButton.addActionListener(l -> {
                    new SchedulePanelLoad().reload(s);
                    new ArchivePanelLoad().reload(s);
                });
                
                FrontEndUtility.setJComponentDimension(reloadButton, new Dimension(100, 40));
                
                publish(reloadButton);
                return null;
            }

            @Override
            protected void process(List<JButton> chunks) {
                reloadPanel.add(chunks.getFirst());
                FrontEndUtility.refresh(reloadPanel);
            }
        }.execute();
    }
    
    public static void addNewTaskSchedule (Schedule schedule, JPanel scheduleTab, Table table, Task task, int rowIndex) // invoke this from a process stack
    {
//        var scheduleTable = schedule.getScheduleTable();
        
        addNewTaskSchedule(table, task, rowIndex);
        
        if (rowIndex==0)
            addFreshNewTask (schedule, scheduleTab, table);            
    }
    
    public static void addNewTaskSchedule (Table table, Task task, int rowIndex) 
    {
        List<Object> rowData = new ArrayList<>();
        rowData.add(false);
        rowData.add(rowIndex+1);
        rowData.add(task.getTitle());
        rowData.add(task.getDescription());
        rowData.add(FrontEndUtility.getAppFormatDate(task.getLocalDate()));
        rowData.add("View");
        
        table.insertTableARow(rowIndex, rowData);        
    }    
    
    public static void addFreshNewTask (Schedule schedule, JPanel tab, Table table) {
        
        Schedule_ArchivePanelLoadContract panelLoad = table instanceof ScheduleTable ? new SchedulePanelLoad() : new ArchivePanelLoad();
        JScrollPane pane = table instanceof ScheduleTable ? schedule.getScheduleTabScrollPane() : schedule.getArchiveTabScrollPane();
        JPanel southPanel = table instanceof ScheduleTable ? schedule.getScheduleSouthPanel(): schedule.getArchiveSouthPanel();
        
        
        if (!pane.equals(FrontEndUtility.getBorderLayoutComponent(tab, BorderLayout.CENTER)))
            panelLoad.removeCenterPanelComponent(tab);
        
        panelLoad.addCenterPanelComponent(tab, pane);
        
        if (!southPanel.equals(FrontEndUtility.getBorderLayoutComponent(tab, BorderLayout.SOUTH)))
            panelLoad.addSouthPanelComponent(tab, southPanel);
        
        panelLoad.populateCenterPanel(pane, table);
        panelLoad.populateSouthPanel(schedule);
        
        if (schedule.getSouthPanelUIElts().getSelectAllBtn().getActionListeners().length == 0)
            panelLoad.addSouthComponentsListeners(schedule);
        
        FrontEndUtility.refresh(tab);        
    }
    
    public static List<Task> readSchedulesOrArchives (String path, JLabel connectionLabel) 
    {
        var sw = new SwingWorker<List<Task>, Object> () {
            @Override
            protected List<Task> doInBackground() throws Exception {
                
                var request = HttpRequest
                        .newBuilder(URI.create(CommonWebServiceFeatures.getBaseURL() + path))
                        .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                        .GET()
                        .build();
                
                HttpResponse<?> response = (HttpResponse<?>) CommonWebServiceFeatures.getHttpResponse(request, connectionLabel);
                
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                
                Type type = new TypeToken<List<Task>>() {}.getType();
                
                List<Task> tasks = gson.fromJson(response.body().toString(), type);

                return tasks;
            }
        };
        
        try {
            sw.execute();
            return sw.get();
        }
        catch(Exception ex) {}
        return new ArrayList<>();
    }
    
    public static void reload (Schedule s, JPanel tab, JScrollPane pane, JTextField textField, ProgressTracker tracker, Table table, Schedule_ArchivePanelLoadContract contract) 
    {
        
        var sw = new SwingWorker<Object, Integer> () {
          
            private int progressTrackerArr[] = new int[2];
            private List<Task> tasks;
            private boolean isShowingScrollPane;
            
            @Override
            protected Object doInBackground() throws Exception 
            {
             
                s.enableComponents(false);
                JComponent component = (JComponent) FrontEndUtility.getBorderLayoutComponent(tab, BorderLayout.CENTER);
                
                isShowingScrollPane = pane.equals(component);
                
                
                if (isShowingScrollPane)
                    SearchableTable.cleanSearchInTable(table);
                
                
                tasks = contract.loadTasks(s);

                if (tasks.isEmpty()) {
                    contract.showNoTaskPanel(s, tab);
                    return null;
                }
                
                progressTrackerArr[0] = 0;
                progressTrackerArr[1] = tasks.size();
                publish(table.getjTable().getRowCount());
                
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                
                Table.removeTableRowsWithProgressTracker(table, tracker, chunks.getFirst()-1, progressTrackerArr, progressTrackerArr[1]);  
                
                if (isShowingScrollPane)
                    addTableRows(tasks);
                else
                    contract.showTasks(s, tasks);
            }
            
            private void addTableRows (List<Task> tasks) {
                
                SwingUtilities.invokeLater(() -> {
                    tasks.forEach(e -> {
                        ScheduleUtility.addNewTaskSchedule(table, e, table.getjTable().getRowCount());
                    });
                });
            }

            @Override
            protected void done() {
                s.enableComponents(true);
            }
        };  
        
        sw.execute();
        try {
            sw.get();
        } catch (Exception e) {}
    }
    
    public static void deleteScheduleServer (List<List<String>> checkedRowsList, String path, JLabel connectionLabel) 
    {
        
        var sw = new SwingWorker<Object, Integer> () {
          
            @Override
            protected Object doInBackground() throws Exception 
            {
                Gson gson = CommonWebServiceFeatures.getRegisteredLocalDateGson();
                
                List<Task> tasks = ScheduleUtility.convertTableRowsToTasksList(checkedRowsList);
                String jsonString = gson.toJson(tasks);

                var request = HttpRequest
                        .newBuilder(URI.create(path))
                        .header("Authorization", CommonWebServiceFeatures.getHttpBasicCredentials())
                        .header("Content-Type", "application/json")
                        .method("DELETE", HttpRequest.BodyPublishers.ofString(jsonString))
                        .build();
                
                var client = CommonWebServiceFeatures.getHttpResponse(request, connectionLabel);
                
                if (client.statusCode() > 299) {
                    new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(client.body().toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException();
                }
                return null;
            }
            
        };  
        
        sw.execute();
        try {
            sw.get();
        } catch (Exception e) {
            throw new RuntimeException("Thrown while deleting a schedule at ScheduleUtility.deleteScheduleServer");
        }        
    }
    
    public static void deleteScheduleClient (Table table, ProgressTracker progressTracker, JComponent[] components) {
        DeleteTableRows deleteTableRows = new DeleteSchedule(table, progressTracker, components);
        new DeleteSelectedTableRows(deleteTableRows).deleteRows();
    }
    
    private static List<Task> convertTableRowsToTasksList (List<List<String>> checkedRowsList) {
        
        List<Task> tasks = new ArrayList<>();
        
        checkedRowsList.forEach(e -> 
        {    
            Date date = FrontEndUtility.getAppFormatDateToSQLFormatDate(e.getLast());
            Task task = new Task(e.getFirst(), e.get(1), LocalDate.parse(date.toString()));
            tasks.add(task);
        });
        
        return tasks;
    }
}
