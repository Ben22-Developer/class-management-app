package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.TabbedPanePanel;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.FrontEndUtility;
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
public abstract class TabbedPanePanel {
    
    public void load(PanelLoad panelLoad) {
        setJTabbedPane();
        setTabsData();
        setSouthReloadPanel(panelLoad);
    }    

    protected void setJTabbedPane (ClassPanel classPanel, JPanel attendancePanel, JTabbedPane tabbedPane) {
        attendancePanel.add(tabbedPane, BorderLayout.CENTER);
        classPanel.setTabbedPaneScrollEvent(tabbedPane);        
    }
    
    protected void setTabsData (List<List<Object>> termAttendanceDetails, int classTermNum) {

        List<List<String>> tableRows = null;
        String currTableIdentifier = "";
        int lessonNum = 0;
        int rowNum = 1;
        
        for (List<Object> rowData : termAttendanceDetails) {
            
            String lessonName = String.valueOf(rowData.get(1));

            if (!currTableIdentifier.equals(lessonName)) {
                
                if (tableRows != null) {
                    rowNum = 1;
                    setLessonCenterPanel(tableRows, lessonNum, getProxy(), getTabPanel(currTableIdentifier));
                }
                currTableIdentifier = lessonName;
                lessonNum = Integer.parseInt(String.valueOf(rowData.get(0)));
                tableRows = new ArrayList<>();
            }

            if (rowData.get(2) == null)
                continue;
            
            List<String> row = tableFormattedData(rowNum, rowData);
            tableRows.add(row);
            rowNum += 1;
        }
        if (tableRows == null)
            return;        
        
        
        setLessonCenterPanel(tableRows, lessonNum, getProxy(), getTabPanel(currTableIdentifier));
    }
    
    private JPanel getTabPanel (String lessonName) {
        
        JTabbedPane pane = getTabbedPane();
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        pane.addTab("",panel);
        
        pane.setTabComponentAt(pane.getTabCount() - 1, getTabTitle(lessonName));    
        
        return panel;
    }
    
    public JPanel getTabTitle (String lessonName) {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 25, 0));
        JLabel titleLabel = new JLabel(getClassPanel().getTitle() +"/" + lessonName); 
        titlePanel.add(titleLabel);
        FrontEndUtility.setJComponentDefaultFont(titleLabel);
        titlePanel.setOpaque(false);
        return titlePanel;
    }    
    
    private void setLessonCenterPanel (List<List<String>> data, int lessonNum, DataEntryActions proxy, JPanel panel) {
        
        Table table = getTable (data, lessonNum);
        var manager = new TableManager(data, lessonNum, getClassPanel(), table, panel, proxy);
        manager.run();
    }  
    
    protected void tabbedPaneReload (JPanel panel, BorderLayout layout) {
        
            for (Component component : new Component[] { layout.getLayoutComponent(BorderLayout.CENTER), layout.getLayoutComponent(BorderLayout.SOUTH) })
                panel.remove(component);
            
            panel.revalidate();
            panel.repaint();        
    }
     
    public abstract void setJTabbedPane();
    public abstract void setTabsData();
    public abstract List<String> tableFormattedData (int rowNum, List<Object> rowObj);
    public abstract void setSouthReloadPanel(PanelLoad panelLoad);
    public abstract ActionListener getReloadBtnListener();
    public abstract Table getTable(List<List<String>> data, int lessonNum);
    public abstract JTabbedPane getTabbedPane();
    public abstract ClassPanel getClassPanel();    
    public abstract DataEntryActions getProxy();
}
