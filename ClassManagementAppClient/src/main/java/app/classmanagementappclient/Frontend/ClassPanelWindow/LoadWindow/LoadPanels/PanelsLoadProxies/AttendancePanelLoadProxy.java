package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies;

import app.classmanagementappclient.Medium.Attendance.AttendanceMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers.PanelDisplayHandler;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.Interface.PanelLoadInterface;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.TabbedPanePanel.TabbedPanePanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AttendanceTable.AttendanceTableManagerProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AttendanceTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class AttendancePanelLoadProxy extends TabbedPanePanel implements PanelLoadInterface{
    
    private ClassPanel classPanel;
    private int classTermNum;
    
    @Override
    public void load(PanelLoad panelLoad, ClassPanel classPanel, int classTermNum) {
        
        this.classPanel = classPanel;
        this.classTermNum = classTermNum;
        super.load(panelLoad);
    }    
    
    @Override
    public void setJTabbedPane () {
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        super.setJTabbedPane(classPanel, classPanel.getAttendancePanel(), tabbedPane);
    }
    
    @Override
    public void setTabsData () {
  
        List<List<Object>> termAttendanceDetails = new AttendanceMedium().getAllAttendanceDetailsInTerm(classTermNum);
        super.setTabsData(termAttendanceDetails, classTermNum);
    }
    
    @Override
    public List<String> tableFormattedData (int rowNum, List<Object> rowObj) {
        
        List<String> row = new ArrayList<>();
        
        row.add(String.valueOf(rowNum) );
        row.add(FrontEndUtility.getAppFormatDate(rowObj.get(2)));
        row.add(FrontEndUtility.getRecordedAtTimeInAppFormat(rowObj.getLast()));        
        return row;
    }
    
    @Override
    public void setSouthReloadPanel (PanelLoad panelLoad) {
        panelLoad.UIForCommonTabbedPanePanel( new PanelDisplayHandler(classPanel) , classPanel.getAttendancePanel(), getReloadBtnListener());
    }     

    @Override
    public ActionListener getReloadBtnListener() {
        return (l) -> 
        {
            JPanel attendancePanel = classPanel.getAttendancePanel();
            BorderLayout layout = (BorderLayout) attendancePanel.getLayout();
            super.tabbedPaneReload(attendancePanel, layout);
            new PanelLoad(classPanel, classTermNum).loadPanel( this );
        };
    }
    
    @Override
    public Table getTable (List<List<String>> data, int lessonNum) {
        return AttendanceTable.getAttendanceTable(true, true, "View", List.of("", "No", "Attendance Date", "Recorded at", "Records"), List.of(2,3), data, lessonNum,300);
    }
    
    @Override
    public JTabbedPane getTabbedPane () {
        
        BorderLayout layout = (BorderLayout) classPanel.getAttendancePanel().getLayout();
        return (JTabbedPane) layout.getLayoutComponent (BorderLayout.CENTER);
    }

    @Override
    public ClassPanel getClassPanel() {
        return classPanel;
    }    

    @Override
    public DataEntryActions getProxy() {
        return new AttendanceTableManagerProxy();
    }
    
    
}
