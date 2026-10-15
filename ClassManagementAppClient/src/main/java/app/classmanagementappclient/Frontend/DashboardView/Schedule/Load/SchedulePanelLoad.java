/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.Load;

import app.classmanagementappclient.Backend.Objects.WebObject.Task.Task;
import app.classmanagementappclient.Frontend.Common.AddNewThingPanel.AddPanel;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Dialog.Descendants.AddNewScheduleDialog;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.ScheduleUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ScheduleTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SchedulePanelLoad implements Schedule_ArchivePanelLoadContract{
    
    public void loadSchedule (Schedule schedule) {
        loadScheduleTab(schedule);
    }
    
    private void loadScheduleTab (Schedule s) {
        
        JPanel panel = s.getScheduleTab();
        
        if (!apiKeyCheckUp()) {
            showNoTaskPanel(s, panel);
            return;
        }
        
        List<Task> tasks = loadTasks(s);
        
        if (tasks.isEmpty())
            showNoTaskPanel(s, panel);
        else
            showTasks(s, tasks);
    }

    @Override
    public boolean apiKeyCheckUp() {
        boolean bool = !CommonWebServiceFeatures.teacherApiKey().isBlank();
        if (!bool)
            CommonWebServiceFeatures.noApiKeyMsg();
        return bool;
    }
    
    
    
    public void showNoTaskPanel (Schedule s, JPanel scheduleTab) {
        
        removeCenterPanelComponent(scheduleTab);
        removeSouthPanelComponent(scheduleTab);
        
        var addPanel = new AddPanel();
        
        JButton newBtn = addPanel.getNewBtn();
        newBtn.setText("Add New Schedule");
        
        newBtn.addActionListener(l -> {
            new AddNewScheduleDialog(s).drawJDialog();
        });
        
        scheduleTab.add(addPanel, BorderLayout.CENTER);
    }
    
    public void showTasks (Schedule s, List<Task> tasks) 
    {
        SwingUtilities.invokeLater(() -> {
            tasks.forEach(e -> {
                ScheduleUtility.addNewTaskSchedule(s, s.getScheduleTab(), s.getScheduleTable(), e, s.getScheduleTable().getjTable().getRowCount());
            });        
        });
    }
    
    public void removeCenterPanelComponent (JPanel scheduleTab) {
        Component component = FrontEndUtility.getBorderLayoutComponent(scheduleTab, BorderLayout.CENTER);
        scheduleTab.remove(component);
    }

    @Override
    public void removeSouthPanelComponent(JPanel scheduleTab) {
        Component component = FrontEndUtility.getBorderLayoutComponent(scheduleTab, BorderLayout.SOUTH);
        if (component != null)
            scheduleTab.remove(component);        
    }    

    public void addCenterPanelComponent (JPanel scheduleTab, Component component) {
        scheduleTab.add(component, BorderLayout.CENTER);
    }    
    
    @Override
    public void addSouthPanelComponent (JPanel scheduleTab, Component component) {
        scheduleTab.add(component, BorderLayout.SOUTH);
    }
    
    public void populateSouthPanel (Schedule s) {
        
        SwingUtilities.invokeLater(() -> 
        {
            JPanel southPanel = s.getScheduleSouthPanel();
            s.getSouthPanelUIElts().setComponentsProp();
            JComponent[] components = getAllComponents(s);
            FrontEndUtility.addJComponentToPanel(southPanel, components);      
        });     
    }
    
    public void populateCenterPanel (JScrollPane scrollPane, Table scheduleTable) {
        
        SwingUtilities.invokeLater(() -> {
            scrollPane.setViewportView(scheduleTable.getjTable());
        });     
    }    
    
    public List<Task> loadTasks (Schedule s) {
         return ScheduleUtility.readSchedulesOrArchives(CommonWebServiceFeatures.readAllSchedules, s.getConnectionLabel());
    }
    
    public void addSouthComponentsListeners (Schedule s) {
        
        Table.setCommonEventHandlers(new JButton[] {getSelectAllBtn(s), getSelectNoneBtn(s)}, s.getScheduleTable());
        
        getReloadBtn(s).addActionListener(l -> {
            reload(s);
        });
        
        getAddNew(s).addActionListener(l -> {
            
            new AddNewScheduleDialog(s).drawJDialog();
        });
        
        getDeleteBtn(s).addActionListener(l -> 
        {            
            ScheduleUtility.deleteScheduleServer(s.getScheduleTable().getOnViewCheckedRowsData(),
                    CommonWebServiceFeatures.getBaseURL()+CommonWebServiceFeatures.deleteSchedules,
                    s.getConnectionLabel());            
            
            ScheduleUtility.deleteScheduleClient(s.getScheduleTable(), s.getSouthPanelUIElts().getTracker(), s.getAllListenerComponents());
        });
        
        JTextField textField = getTextField(s);
        ScheduleTable table = s.getScheduleTable();
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(getSearchTxt(s)))
                    textField.setText("");   
            }
        });
        
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                
                if (table.getjTable().getModel().getRowCount() == 0)
                    return;
                SearchableTable searchableTable = (SearchableTable) table;
                searchableTable.searchInTable(textField.getText(), table);
            }
        });
    }

    @Override
    public void reload(Schedule s) {
        ScheduleUtility.reload(s, s.getScheduleTab(), s.getScheduleTabScrollPane(), getTextField(s), getTracker(s), s.getScheduleTable(), this);
    }
    
    public JButton getSelectAllBtn(Schedule s) {
        return s.getSouthPanelUIElts(). getSelectAllBtn();
    }

    public JButton getSelectNoneBtn(Schedule s) {
        return s.getSouthPanelUIElts(). getSelectNoneBtn();
    }

    public JButton getReloadBtn(Schedule s) {
        return s.getSouthPanelUIElts(). getReloadBtn();
    }

    public JButton getDeleteBtn(Schedule s) {
        return s.getSouthPanelUIElts(). getDeleteBtn();
    }

    public JButton getAddNew(Schedule s) {
        return s.getSouthPanelUIElts(). getAddNew();
    }

    public String getSearchTxt(Schedule s) {
        return s.getSouthPanelUIElts(). getSearchTxt();
    }

    public JTextField getTextField(Schedule s) {
        return s.getSouthPanelUIElts(). getTextField();
    }

    public JProgressBar getProgressBar(Schedule s) {
        return s.getSouthPanelUIElts(). getProgressBar();
    }

    public ProgressTracker getTracker(Schedule s) {
        return s.getSouthPanelUIElts(). getTracker();
    }   
    
    public JComponent[] getAllComponents (Schedule s) {
        return new JComponent[] { getSelectAllBtn(s), getSelectNoneBtn(s), getReloadBtn(s), 
            getAddNew(s), getDeleteBtn(s), getTextField(s), getProgressBar(s) };
    }
}
