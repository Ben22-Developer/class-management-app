package app.classmanagementappclient.Frontend.MainWindow.UIView.Save.Contract;

/**
 *
 * @author BEN
 */
public interface SaveUIContract {
    void run();
    void showingPanel();
    void panelChild(StringBuilder panelChildsBuilder);
    void storePanelChildKeys(StringBuilder panelChildsBuilder);
    void selectedChild();    
}
