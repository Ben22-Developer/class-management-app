package app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.Handlers;

import app.classmanagementappclient.Frontend.TreeWindow.JTreeListeners.Key_MouseHandlers.TreePopUpHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author BEN
 */
public class KeyBoardEventHandler extends KeyAdapter
{
    private final TreePopUpHandler popUpHandler;

    public KeyBoardEventHandler(TreePopUpHandler popUpHandler) {
        this.popUpHandler = popUpHandler;
    }
    
    @Override
    public void keyReleased(KeyEvent e) 
    {    
        JTree jt = (JTree) e.getSource();
        
        int[] rows = jt.getSelectionRows();
        if (rows.length == 0)
            return;
        
        Rectangle rect = jt.getPathBounds(jt.getSelectionPath());
        int x = 96;
        int y = rect.y;        
        popUpHandler.eventHandler(jt, rows[0], x, y);
    }
}
