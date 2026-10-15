package app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class DeleteSelectedTableRows {
    
    private final DeleteTableRows proxy;

    public DeleteSelectedTableRows(DeleteTableRows proxy) {
        this.proxy = proxy;
    }

    public void deleteRows  () {
        
        SwingWorker<Object, List<Integer>> sw = new SwingWorker <Object, List<Integer>> () {

            @Override
            protected Object doInBackground() throws Exception {
                proxy.doInBackGround();
                List<Integer> published = proxy.getPublishedData();
                publish (published);
                return null;
            }
            
            @Override
            protected void process(List<List<Integer>> listOfList) {
                for (List<Integer> list : listOfList) 
                    proxy.process(list);
                
            }
            
            @Override
            public void done () {
                proxy.done();
            }
            
        };
        sw.execute();
    } 
}
