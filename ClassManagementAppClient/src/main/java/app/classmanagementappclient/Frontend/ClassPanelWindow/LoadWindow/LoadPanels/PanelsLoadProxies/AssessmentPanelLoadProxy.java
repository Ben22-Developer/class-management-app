package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies;

import app.classmanagementappclient.Medium.Assessment.AssessmentMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers.PanelDisplayHandler;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.Interface.PanelLoadInterface;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.TabbedPanePanel.TabbedPanePanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.AssessmentTable.AssessmentTableManagerProxy;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.AssessmentsTable;
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
public class AssessmentPanelLoadProxy extends TabbedPanePanel implements PanelLoadInterface {

    private ClassPanel classPanel;
    private int classTermNum;
    
    @Override
    public void load(PanelLoad panelLoad, ClassPanel classPanel, int classTermNum) {
        
        this.classPanel = classPanel;
        this.classTermNum = classTermNum;
        super.load(panelLoad);
    }     
    
    @Override
    public void setJTabbedPane() {
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        super.setJTabbedPane(classPanel, classPanel.getAssessmentsPanel(), tabbedPane);
    }

    @Override
    public void setTabsData() {
        List<List<Object>> data = new AssessmentMedium().getAllAssessmentsInTerm(classTermNum);
        super.setTabsData(data, classTermNum);
    }

    @Override
    public List<String> tableFormattedData(int rowNum, List<Object> rowObj) {
        
        List<String> rowData = new ArrayList<>();
        
        rowData.add(String.valueOf(rowNum));
        rowData.add (String.valueOf(rowObj.get(2)));
        rowData.add (FrontEndUtility.getAppFormatDate(rowObj.get(3)));
        rowData.add (String.valueOf(rowObj.get(4)));
        return rowData;
    }
    
    @Override
    public void setSouthReloadPanel(PanelLoad panelLoad) {
        panelLoad.UIForCommonTabbedPanePanel( new PanelDisplayHandler(classPanel) , classPanel.getAssessmentsPanel(), getReloadBtnListener());
    }    

    @Override
    public ActionListener getReloadBtnListener() {
        return (l) -> {
            JPanel assessmentPanel = classPanel.getAssessmentsPanel();
            BorderLayout layout = (BorderLayout) assessmentPanel.getLayout();
            super.tabbedPaneReload(assessmentPanel, layout);
            new PanelLoad(classPanel, classTermNum).loadPanel( this );
        };
    }

    @Override
    public Table getTable(List<List<String>> data, int lessonNum) {
        return AssessmentsTable.getAssessmentsTable(data, lessonNum, 200);
    }

    @Override
    public JTabbedPane getTabbedPane() {
        BorderLayout layout = (BorderLayout) classPanel.getAssessmentsPanel().getLayout();
        return (JTabbedPane) layout.getLayoutComponent (BorderLayout.CENTER);    
    }

    @Override
    public ClassPanel getClassPanel() {
        return classPanel;
    }

    @Override
    public DataEntryActions getProxy() {
        return new AssessmentTableManagerProxy();
    }
}
