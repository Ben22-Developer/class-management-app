/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Load.DashboardPanel.SchedulePanel;

import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.Menu;
import app.classmanagementappclient.Frontend.DashboardView.DashboardMenuComponents.MenuItem;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.Schedule;
import app.classmanagementappclient.Frontend.DashboardView.Schedule.ScheduleEnum.DisplayingScheduleEnum;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;

import javax.swing.*;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class SchedulePanelLoad {
    
    private final Menu menu;
    private final MenuItem menuItem;
    private Schedule schedule;
    
    private final Properties uiViewProperties; 

    public SchedulePanelLoad(Menu menu, MenuItem menuItem, Properties uiViewProperties) {
        
        this.menu = menu;
        this.menuItem = menuItem;
        
        this.uiViewProperties = uiViewProperties;
    }
    
    public void run () {
        
        fireEvent();
        setSchedule();
        selectedChildPanel();
    }
    
    private void fireEvent () {
        menu.addActionListener("Schedule", menuItem);
    }
    
    private void selectedChildPanel () {
        
        String key = UIViewConstants.PanelSelectedChild.toString();
        Object object = uiViewProperties.get(key);
        
        DisplayingScheduleEnum scheduleEnum = DisplayingScheduleEnum.valueOf( String.valueOf(object) );
        JTabbedPane pane = schedule.getjTabbedPane1();
        
        switch (scheduleEnum) {
            
            case DisplayingScheduleEnum.ActiveSchedule ->  pane.setSelectedIndex(0);
            case DisplayingScheduleEnum.ArchiveSchedule -> pane.setSelectedIndex(1);
            default -> throw new AssertionError("Unknown displayingSchedule");
        }
        
    }

    private void setSchedule() {
        this.schedule = menu.getSchedule();;
    }
}
