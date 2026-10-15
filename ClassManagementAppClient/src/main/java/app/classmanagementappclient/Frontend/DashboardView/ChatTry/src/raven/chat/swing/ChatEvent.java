package app.classmanagementappclient.Frontend.DashboardView.ChatTry.src.raven.chat.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public interface ChatEvent {

    public void mousePressedSendButton(ActionEvent evt);

    public void mousePressedFileButton(ActionEvent evt);

    public void keyTyped(KeyEvent evt);
}
