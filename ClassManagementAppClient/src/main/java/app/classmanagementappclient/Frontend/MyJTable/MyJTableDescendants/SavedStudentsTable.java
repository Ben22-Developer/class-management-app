package app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants;

import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.SearchableTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author BEN
 */
public class SavedStudentsTable extends Table implements SearchableTable {
    
    private final JProgressBar progressBar = new JProgressBar();
    private final ProgressTracker progressTracker = new ProgressTracker(progressBar);
    
    public void runProgress () {
        progressTracker.run();
    }    
    
    public void endProgress () {
        progressTracker.endProgress();
    }

    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }    

    public JProgressBar getProgressBar() {
        return progressBar;
    }      
    
    public static SavedStudentsTable getTable (List<List<String>> rowsData, int...clmWidth) {
        
        SavedStudentsTable table = new SavedStudentsTable();
        table.setup(table, EditableTableType.IsSavedStudentsTable, true, true, "Edit", List.of("", "Num", "Student ID", "Student Name", "Edit"), List.of(2,3), rowsData, clmWidth);
        return table;
    }    
}
   
