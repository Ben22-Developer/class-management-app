package app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows;

import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface DeleteTableRows {
    public void doInBackGround ();
    public void process (List<Integer> list);
    public void done();
    public boolean isDoInBgProcessEnded();
    public List<Integer> getPublishedData();
    public Table getTable();
}
