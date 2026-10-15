package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing.scroll;

import javax.swing.*;
import java.awt.*;

public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
        setOpaque(false);
        setUnitIncrement(20);
    }
}
