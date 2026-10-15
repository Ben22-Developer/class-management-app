package app.classmanagementappclient.Frontend.DoubleClickables;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author BEN
 */
public class TimerHandler extends MouseAdapter implements ActionListener {
    
    private final DoubleClickable doubleClickable;
    private final Timer timer = new Timer(300, this);
    private boolean isDoubleClick;

    public TimerHandler(DoubleClickable doubleClickable) {
        this.doubleClickable = doubleClickable;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        startTimer();
        isDoubleClick = e.getClickCount() > 1;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (isDoubleClick)
            doubleClickable.doubleClickHandler();
        else
            doubleClickable.clickHandler();
        
        timer.stop();
    }   
    
    private void startTimer () {
        if (timer.isRunning())
            return;
        timer.start();
    }
}
