/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.Schedule.Load;

import app.classmanagementappclient.Backend.Objects.WebObject.Task.Task;
import app.classmanagementappclient.Frontend.Common.WebService.CommonWebServiceFeatures;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.ScheduleUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
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
public class ArchivePanelLoad implements Schedule_ArchivePanelLoadContract{

    @Override
    public void loadSchedule(Schedule s) {
        
        JPanel panel = s.getArchiveTab();
        
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
        return !CommonWebServiceFeatures.teacherApiKey().isBlank();
    }

    @Override
    public void showNoTaskPanel(Schedule schedule, JPanel scheduleTab) {

        removeCenterPanelComponent(scheduleTab);
        removeSouthPanelComponent(scheduleTab);
        
        JPanel panel = new JPanel(new BorderLayout());
        var label = new JLabel("No archives");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);
        scheduleTab.add(panel, BorderLayout.CENTER);        
    }

    @Override
    public void showTasks(Schedule schedule, List<Task> tasks) {
        tasks.forEach(e -> {
            ScheduleUtility.addNewTaskSchedule(schedule, schedule.getArchiveTab(), schedule.getArchiveTable(), e, schedule.getArchiveTable().getjTable().getRowCount());
        });    
    }

    @Override
    public void removeCenterPanelComponent(JPanel scheduleTab) {
        var layout = (BorderLayout) scheduleTab.getLayout();
        Component component = layout.getLayoutComponent(BorderLayout.CENTER);
        scheduleTab.remove(component);    
    }
    
    @Override
    public void removeSouthPanelComponent(JPanel scheduleTab) {
        Component component = FrontEndUtility.getBorderLayoutComponent(scheduleTab, BorderLayout.SOUTH);
        if (component != null)
            scheduleTab.remove(component);        
    }       

    @Override
    public void addCenterPanelComponent(JPanel scheduleTab, Component component) {
        scheduleTab.add(component, BorderLayout.CENTER);
    }
    
    @Override
    public void addSouthPanelComponent (JPanel scheduleTab, Component component) {
        scheduleTab.add(component, BorderLayout.SOUTH);
    }

    @Override
    public void populateSouthPanel(Schedule s) {
        
        SwingUtilities.invokeLater(() -> 
        {
            JPanel southPanel = s.getArchiveSouthPanel();
            s.getArchiveSouthPanelUIElts().setComponentsProp();
            JComponent[] components = getAllComponents(s);
            FrontEndUtility.addJComponentToPanel(southPanel, components);     
        }); 
    }

    @Override
    public void populateCenterPanel(JScrollPane scrollPane, Table scheduleTable) {
        SwingUtilities.invokeLater(() -> {
            scrollPane.setViewportView(scheduleTable.getjTable());
        });             
    }

    @Override
    public List<Task> loadTasks(Schedule s) {
        return ScheduleUtility.readSchedulesOrArchives(CommonWebServiceFeatures.readAllSchedulesArchive, s.getConnectionLabel());
    }

    @Override
    public void addSouthComponentsListeners(Schedule s) {

        Table.setCommonEventHandlers(new JButton[] {getSelectAllBtn(s), getSelectNoneBtn(s)}, s.getArchiveTable());
        
        getReloadBtn(s).addActionListener(l -> {
            reload(s);
        });
        
        getDeleteBtn(s).addActionListener(l -> {
            
            ScheduleUtility.deleteScheduleServer(s.getArchiveTable().getOnViewCheckedRowsData(),
                    CommonWebServiceFeatures.getBaseURL()+CommonWebServiceFeatures.deleteArchiveSchedules,
                    s.getConnectionLabel());
            
            ScheduleUtility.deleteScheduleClient(s.getArchiveTable(), s.getSouthPanelUIElts().getTracker(), s.getAllListenerComponents());
        });
        
        JTextField textField = getTextField(s);
        var table = s.getArchiveTable();
        
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
        
        if (!apiKeyCheckUp())
            return;
        
        ScheduleUtility.reload(s, s.getArchiveTab(),  s.getArchiveTabScrollPane(), getTextField(s), getTracker(s), s.getArchiveTable(), this);
    }
    
    public JButton getSelectAllBtn(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getSelectAllBtn();
    }

    public JButton getSelectNoneBtn(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getSelectNoneBtn();
    }

    public JButton getReloadBtn(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getReloadBtn();
    }

    public JButton getDeleteBtn(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getDeleteBtn();
    }

    public String getSearchTxt(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getSearchTxt();
    }

    public JTextField getTextField(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getTextField();
    }

    public JProgressBar getProgressBar(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getProgressBar();
    }

    public ProgressTracker getTracker(Schedule s) {
        return s.getArchiveSouthPanelUIElts(). getTracker();
    }   
    
    public JComponent[] getAllComponents (Schedule s) {
        return new JComponent[] { getSelectAllBtn(s), getSelectNoneBtn(s), getReloadBtn(s), 
            getDeleteBtn(s), getTextField(s), getProgressBar(s) };
    }    
}
