/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.Common.WebService;

import javax.swing.*;

/**
 *
 * @author BEN
 */
abstract public class WebService {

    private final JLabel connectionLabel;
    
    public WebService(JLabel connectionLabel) {
        this.connectionLabel = connectionLabel;
    }

    public JLabel getConnectionLabel() {
        return connectionLabel;
    }
}
