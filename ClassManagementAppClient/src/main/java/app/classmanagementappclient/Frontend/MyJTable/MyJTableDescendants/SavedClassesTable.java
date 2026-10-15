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
public class SavedClassesTable extends Table implements SearchableTable
{
    private final JProgressBar progressBar = new JProgressBar();
    private final ProgressTracker progressTracker = new ProgressTracker(progressBar);
    private final JTree jTree;  

    public SavedClassesTable(JTree jTree) {
        this.jTree = jTree;
    }
    
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
    
    public JTree getjTree() {
        return jTree;
    }
    
    public static SavedClassesTable getTable (List<List<String>> rowsData, JTree jTree, int...clmWidth) {
        
        SavedClassesTable table = new SavedClassesTable(jTree);
        table.setup(table, EditableTableType.IsSavedClassesTable, true, false, "", List.of("","Num","Schools", "Classes", "Years", "Terms", "Lessons"), List.of(2,3,4,5,6), rowsData, clmWidth);
        return table;
    }    
}
