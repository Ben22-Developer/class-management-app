package app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers;

import app.classmanagementappclient.Frontend.FrontEndUtility;

import javax.swing.*;
import javax.swing.tree.TreePath;

/**
 *
 * @author BEN
 */
public class TreePopUpHandler
{
    private JPopupMenu multiPopupMenu;
    private JPopupMenu[] popupMenus = new JPopupMenu[5];
    
    public void eventHandler (JTree jt, int row, int x, int y) {
        if (row != -1) 
        {
           boolean displayOnePopUp = displayAPopUp(jt, x, y);
            if (displayOnePopUp)
                onePopUpDisplay(jt, x, y);
            else
                multiPopUpDisplay(jt, x, y);
        }        
    }
    
    private boolean displayAPopUp (JTree jt, int x, int y)
    {
        TreePath[] treePaths = jt.getSelectionPaths();
        if (treePaths == null || treePaths.length == 1) {
            jt.setSelectionPath(jt.getPathForLocation(x,y));
            return true;
        }
        return false;
    }
    
    private void onePopUpDisplay (JTree jt, int x, int y) {
        int popUpIndex = jt.getLeadSelectionPath().getPath().length - 1;
        FrontEndUtility.setPopupMenuColor( popupMenus[popUpIndex]);
        popupMenus[popUpIndex].show(jt, x, y);        
    }
    
    private void multiPopUpDisplay (JTree jt, int x, int y){
        FrontEndUtility.setPopupMenuColor(multiPopupMenu);
        multiPopupMenu.show(jt, x, y);        
    }    


    public void setPopupMenus(JPopupMenu[] popupMenus) {
        this.popupMenus = popupMenus;
    }

    public void setMultiPopupMenus(JPopupMenu multiPopupMenu) {
        this.multiPopupMenu = multiPopupMenu;
    }
}
