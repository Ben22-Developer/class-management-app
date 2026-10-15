/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.MainWindow.UIView.Save.ClassPanelUI;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.MainWindow.UIView.Enums.UIViewConstants;

import java.util.List;
import java.util.Properties;

/**
 *
 * @author BEN
 */
public class ClassPanelUISave {
    
    private final MainWindow mw;
    private final Properties uiViewProperties;
    
    public ClassPanelUISave(MainWindow mw, Properties uiViewProperties) {
        this.mw = mw;
        this.uiViewProperties = uiViewProperties;
    }
    
    
    public void run () {
        
        showingPanel();
        
        var panelChildsBuilder = new StringBuilder();
        panelChild(panelChildsBuilder);
        storePanelChildKeys(panelChildsBuilder);
        
        selectedChild();
    }    
    
    public void showingPanel () {
        uiViewProperties.put(UIViewConstants.ShowingPanel.toString(), UIViewConstants.ClassPanel.toString());
    }
    
    public void panelChild (StringBuilder panelChildsBuilder) {
        
        List<ClassPanel> classPanelList = mw.getClassPanelList();
        
        
        for (int i = 0; i < classPanelList.size(); i++) 
        {
            var classPanel = classPanelList.get(i);
            var panelChildKey = UIViewConstants.PanelChild.toString()+i;
            
            panelChildsBuilder.append(panelChildKey).append(i == classPanelList.size()-1 ? "" : ",");
            
            savePanelChildProperties(panelChildKey,
                    classPanel.getDisplayingCardNum(), 
                    classPanel.isShowingSettingsPanel(), 
                    classPanel.isShowingTitlePanel(),
                    classPanel.getSchoolLabel().getText() + "/"+ classPanel.getClassNameLabel().getText() + "/" + classPanel.getYearLabel().getText()+ "/" + classPanel.getTermLabel().getText()
            );
        }   
    }
    
    public void storePanelChildKeys (StringBuilder panelChildsBuilder) {
        uiViewProperties.put(UIViewConstants.AllPanelChildKey.toString(), panelChildsBuilder.toString());
    }
    
    private void savePanelChildProperties (String key, int displayingCardNum, boolean  showingSettingPanel, boolean showingTitlePanel, String title) {
        uiViewProperties.put(key, String.valueOf(displayingCardNum + ","+showingSettingPanel+","+showingTitlePanel+","+title));
    }
    
    public void selectedChild () {
        uiViewProperties.put(UIViewConstants.PanelSelectedChild.toString(), String.valueOf(mw.getJTabbedPane().getSelectedIndex()));
    }
}
