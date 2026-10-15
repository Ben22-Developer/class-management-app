package app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.Handlers;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassWindowUtility;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MainWindow.MainWindow;
import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.TreePopUpHandler;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author BEN
 */
public class MouseEventHandler extends MouseAdapter
{
    private final TreePopUpHandler popUpHandler;
    private final MainWindow mainWindow;

    public MouseEventHandler(TreePopUpHandler popUpHandler, MainWindow mainWindow) {
        this.popUpHandler = popUpHandler;
        this.mainWindow = mainWindow;
    }
    
    @Override 
    public void mouseReleased(MouseEvent e) 
    {
        if (e.getClickCount() == 2)
            showClassPanel(e);
        
        if (!e.isPopupTrigger())
            return;
            
        if (!FrontEndUtility.isTeacherLoggedIn()) {
            FrontEndUtility.showMsgJOptionPane("Please log in to access this functionality!", "Informational Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTree jt = (JTree) e.getComponent();
        int x = e.getX();
        int y = e.getY();        
        int row = jt.getRowForLocation(x, y);
        popUpHandler.eventHandler(jt, row, x, y);
    }

    private void showClassPanel (MouseEvent e) {

        JTree jt = (JTree) e.getComponent();
        TreePath path =  jt.getLeadSelectionPath();
        
        if ( path.getPathCount() != 5 )
            return;
        
        ClassWindowUtility.showClass(mainWindow);
    }
}
