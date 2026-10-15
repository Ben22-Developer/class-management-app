package app.classmanagementappclient.Frontend.Common;

import app.classmanagementappclient.Frontend.AggregateWindow.TermsDialog.TermsDialog;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BEN
 */

public interface TermsDialogSubmitHandler {
    
    String setDetails(ConcurrentHashMap<Integer, Integer> termNumsToTerm, TableDisplay display, TermsDialog dialog);
    void setTerm (Iterator<Integer> iterator);
}
