/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.RoundPanels;

import app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.RoundPanel;
import com.formdev.flatlaf.FlatLaf;

import java.awt.*;

/**
 *
 * @author BEN
 */
// BottomRoundPanel BodyRoundPanel

public class BodyRoundPanel extends RoundPanel{

    public BodyRoundPanel() {
        super();
    }
 
    @Override
    protected void paintComponent(Graphics grphcs) {
        setBackground(FlatLaf.isLafDark() ? new Color(255, 255, 255, 20) : Color.WHITE);
        super.paintComponent(grphcs);        
    }    
}
