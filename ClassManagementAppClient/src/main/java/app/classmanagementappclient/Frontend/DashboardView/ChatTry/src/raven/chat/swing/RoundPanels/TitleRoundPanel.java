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
public class TitleRoundPanel extends RoundPanel {

    public TitleRoundPanel() {
        super();
    }
 
    @Override
    protected void paintComponent(Graphics grphcs) {
        setBackground(FlatLaf.isLafDark() ? new Color(255, 255, 255, 20) : Color.LIGHT_GRAY);
        super.paintComponent(grphcs);
        
    }   
}
