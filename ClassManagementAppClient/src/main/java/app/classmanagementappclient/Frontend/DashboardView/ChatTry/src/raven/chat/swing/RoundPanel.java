package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    private int round;

    public RoundPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);
        g2.dispose();
//        setBackground(FlatLaf.isLafDark() ? new Color(255, 255, 255, 20) : new Color(36, 104, 155));
        super.paintComponent(grphcs);
        
    }
}
