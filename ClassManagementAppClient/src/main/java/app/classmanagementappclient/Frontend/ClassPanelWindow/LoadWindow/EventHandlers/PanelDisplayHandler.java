package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.EventHandlers;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassesWithLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassesWithStudentsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;
import app.classmanagementappclient.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author BEN
 */
public class PanelDisplayHandler {
    
    private final ClassPanel classPanel;

    public PanelDisplayHandler(ClassPanel classPanel) {
        this.classPanel = classPanel;
    }
    
    public PanelDisplayHandler() {
        this(null);
    }    
    
    public void setWindowToDisplay (int childPanelNbr)
    {
        JPanel cardsPanel = classPanel.getCardsPanel();
        CardLayout layout = (CardLayout) cardsPanel.getLayout();
        JPanel childPanel =  (JPanel) cardsPanel.getComponent(childPanelNbr);
        
        showCard(layout, cardsPanel, childPanelNbr);
        setTitlePanel(childPanel);
        setActivePanel(childPanel);
    }
    
    
    private void showCard (CardLayout layout, JPanel cardsPanel, int childPanelNbr) {
        
        switch (childPanelNbr) {
            
            case 0 -> {
                layout.show(cardsPanel, "card2");
            }
            case 1 -> {
                layout.show(cardsPanel, "card3");
            }
            case 2 -> {
                layout.show(cardsPanel, "card4");
            }
            case 3 -> {
                layout.show(cardsPanel, "card5");
            }
        }        
    }
    
    
    private void setTitlePanel (JPanel childPanel) {
        
        JPanel title = classPanel.getTitlePanel();
        
        if (title.isVisible())
            childPanel.add(title, BorderLayout.NORTH);
        panelRevalidate(childPanel);
    }
    
    private void setActivePanel (JPanel activePanel) {
        classPanel.setActivePanel(activePanel);
    }

    public void toggleClassWindowSettingsPanel () 
    {
        JPanel panel = classPanel.getSettingsPanel();
        if (panel.isVisible()) 
            hideSettingsPanel(panel);
        else
            showSettingsPanel(panel);
    }

    public void hideSettingsPanel (JPanel panel) {
        panel.setVisible(false);
        classPanel.remove(panel);
        classPanel.validate();
    }
    
    public void showSettingsPanel (JPanel panel) {
        panel.setVisible(true);
        classPanel.add(panel, BorderLayout.WEST);
        panelRevalidate(classPanel);
    }    
    
    public void showTitlePanel (JPanel activePanel, JPanel titlePanel) {
        
        boolean isVisible = titlePanel.isVisible();

        if (!isVisible) {
            activePanel.add(titlePanel, BorderLayout.NORTH);
            titlePanelValidation(activePanel, titlePanel, !isVisible);
        }
    }
    
    public void hideTitlePanel (JPanel activePanel, JPanel titlePanel) {
        
        boolean isVisible = titlePanel.isVisible();
        if (isVisible) {
            activePanel.remove(titlePanel);
            titlePanelValidation(activePanel, titlePanel, !isVisible);
        }
    }
    
    public void titlePanelValidation (JPanel studentsPanel, JPanel titlePanel, boolean isVisible) {
        titlePanel.setVisible (isVisible);
        panelRevalidate(studentsPanel);
    }
    
    public void showAddDataByInputWindowPanel (JPanel mainPanel, JLabel label, JButton button) {

        BorderLayout layout = (BorderLayout) mainPanel.getLayout();
        
        if (layout.getLayoutComponent (BorderLayout.NORTH) != null)
            return;
        
        JPanel panel = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 10));

        button.setBackground(Color.green);
        
        FrontEndUtility.setJComponentsCustomFont(new JComponent[]{label, button}, new Font("Arial", Font.BOLD, 18));
        FrontEndUtility.addJComponentToPanel(panel, new JComponent[]{label, button});
        
        mainPanel.add(panel, BorderLayout.NORTH);
        panelRevalidate(mainPanel);
    }
    
    public void addDataByInputWindowButtonEventListener (JButton button, ActionListener listener) {        
        button.addActionListener (listener);        
    }
    
    
    public JPanel showClassWithStudentsPanel (JPanel mainPanel, String labelTitle) {
        BorderLayout layout = (BorderLayout) mainPanel.getLayout();
        
        if (layout.getLayoutComponent (BorderLayout.CENTER) != null) {
            mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            mainPanel.revalidate();
        }
        JPanel centerPanel = new JPanel (new BorderLayout(0,5));
        JPanel titlePanel = new JPanel();
        JPanel guiComponentsPanel = new JPanel();
        
        setCenterPanelTitleLabel(titlePanel, labelTitle);
        centerPanel.add(titlePanel, BorderLayout.NORTH);
        centerPanel.add(guiComponentsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.revalidate();  
        return centerPanel;
    }
    
    private void setCenterPanelTitleLabel (JPanel titlePanel, String labelTitle) {
        JLabel label = new JLabel(labelTitle);
        FrontEndUtility.setJComponentCustomFont(label, new Font("Arial", Font.BOLD, 18));        
        titlePanel.add(label);
    }
    
    public void setTable (JPanel centerPanel, List<List<String>> tableDetails ,int classTermNum, EditableTableType tableType, List<String> headers, List<Integer> clmsToCheck, int clmWidth) {
        JScrollPane tablePane = new JScrollPane();
        setCenterPanelTable(tablePane, tableDetails, classTermNum, tableType, headers, clmsToCheck, clmWidth);
        centerPanel.add(tablePane, BorderLayout.CENTER);
        centerPanel.revalidate();
    }    
    
    private void setCenterPanelTable (JScrollPane tablePane, List<List<String>> tableDetails ,int classTermNum, EditableTableType tableType, List<String> headers, List<Integer> clmsToCheck, int clmWidth) {
        Table table = switch (tableType) {
            case IsShowingClassWithStudentsTable -> ClassesWithStudentsTable.getClassWithStudentsTable(false, true, "View", headers, clmsToCheck, tableDetails, classTermNum, clmWidth);
            case IsShowingClassWithLessonsTable ->    ClassesWithLessonsTable.getClassWithLessonsTable(false, true, "View", headers, clmsToCheck, tableDetails, classTermNum, clmWidth);
            default -> throw new AssertionError();
        };
        tablePane.setViewportView(table.getjTable());
    }
    
    public JButton showReloadPanel (String errMsg, JPanel mainPanel) {
        
        BorderLayout layout = (BorderLayout) mainPanel.getLayout();     
        
        if (layout.getLayoutComponent (BorderLayout.SOUTH) != null) {
            new FrontEndUtility().runShowMsgJOptionPaneSwingWorker(errMsg, "Error Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        JButton reloadBtn = getReloadBtn(mainPanel);
        return reloadBtn;
    }
    
    public JButton showReloadPanel (JPanel mainPanel) {
        
        JButton button = getReloadBtn(mainPanel);
        button.setOpaque(false);
        button.setBackground(Color.black);
        return button;
    }
    
    public void setPanelBgColor (JPanel parentPanel, String borderLayoutConstraint) {
        
        BorderLayout layout = (BorderLayout) parentPanel.getLayout();
        JPanel panel = (JPanel) layout.getLayoutComponent(borderLayoutConstraint);
        panel.setOpaque(true);
        panel.setBackground(new Color(150, 150, 160));
    }
    
    private JButton getReloadBtn (JPanel mainPanel) {
        
        ImageIcon icon = new ImageIcon (String.valueOf( Main.getApplicationFilesPaths().getProperty("ReloadIcon") ));
        
        JButton reloadBtn = new JButton (icon);
        
        JPanel reloadPanel = new JPanel();
        reloadPanel.setOpaque(false);
        reloadPanel.add(reloadBtn);
        
        mainPanel.add(reloadPanel, BorderLayout.SOUTH);
        panelRevalidate(mainPanel);        
        
        return reloadBtn;
    }
    
    public void setReloadBtnEvent (JButton reloadBtn, ActionListener listener) {
        reloadBtn.addActionListener(listener);        
    }
    
    private void panelRevalidate (JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }    
}
