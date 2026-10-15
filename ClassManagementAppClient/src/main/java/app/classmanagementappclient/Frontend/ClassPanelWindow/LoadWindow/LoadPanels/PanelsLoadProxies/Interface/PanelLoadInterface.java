package app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.Interface;

import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;

import java.awt.event.ActionListener;

/**
 *
 * @author BEN
 */
public interface PanelLoadInterface {
    public void load(PanelLoad panelLoad, ClassPanel classPanel, int classTermNum);
    public ActionListener getReloadBtnListener();
}
