package app.classmanagementappclient.Frontend.MyOptionPanes;

import javax.swing.*;
/**
 *
 * @author BEN
 */
public class JOptionPanes 
{
    public static void showMessage (String message, String msgType, int messageType) {
        JOptionPane.showMessageDialog(null, message, msgType, messageType);
    }
    
    public static String getUserInput (String message) {
        String userInput = JOptionPane.showInputDialog(message);
        return userInput == null ? "" : userInput;
    }
}
