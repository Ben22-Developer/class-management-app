package app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerProxies.LessonsTable;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Frontend.ClassPanelWindow.AddTableData.InsertLessonDialog;
import app.classmanagementappclient.Frontend.ClassPanelWindow.ClassPanel;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.DeleteTableDataProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.DeleteTableData.TableDelete.ClassLessonsTableDelete;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelLoad;
import app.classmanagementappclient.Frontend.ClassPanelWindow.LoadWindow.LoadPanels.PanelsLoadProxies.LessonPanelLoadProxy;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManager.TableManager;
import app.classmanagementappclient.Frontend.ClassPanelWindow.TableManagerInterface.DataEntryActions;
import app.classmanagementappclient.Frontend.JProgress.ProgressTracker;
import app.classmanagementappclient.Frontend.MyJTable.DeleteTableRows.DeleteSelectedTableRows;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ClassLessonsTableManagerProxy implements DataEntryActions {
    
    @Override
    public void addNoToData(List<List<String>> data) {
        
        List<String> lessonNames = data.get(0);
        int i = 1;
        data.clear();
        for (String lessonName : lessonNames) {
            List<String> dataPiece = new ArrayList<>();
            dataPiece.add(String.valueOf(i));
            dataPiece.add(lessonName);
            data.add(dataPiece);
            i += 1;
        }
    }

    @Override
    public List<List<String>> getData(int classTermNum) {
        List<String> lessonNames = new ClassLessonMedium().getAllLessonsInTerm(classTermNum);
        List<List<String>> data = new ArrayList<>();
        if (!lessonNames.isEmpty()) {
            data.add(lessonNames);
            addNoToData(data);
        }
        return data;
    }

    @Override
    public void insertTableRow(Table table, List<String> row) {
        table.insertTableARow(table.getjTable().getRowCount(), List.of (false, row.get(0), row.get(1), "Edit") );
    }

    @Override
    public void handleNoDataReload(TableManager tableManager, ClassPanel panel, int classTermNum) {
        tableManager.getMyPanel().removeAll();
        tableManager.panelReValidation();
        new PanelLoad(panel, classTermNum).loadPanel( new LessonPanelLoadProxy() );
    }

    @Override
    public void deleteHandler(Table table, TableManager tableManager, ProgressTracker progressTracker) {
        DeleteTableDataProxy proxy = new ClassLessonsTableDelete((ClassLessonsTable) table, tableManager, progressTracker);
        new DeleteSelectedTableRows(proxy).deleteRows();
    }

    @Override
    public Object[] addNewData(Table table, int classTermNum) {
        InsertLessonDialog dialog = new InsertLessonDialog(classTermNum, table.getjTable().getRowCount());
        dialog.drawJDialog();
        Object[] objects = dialog.getRes();
        return objects;
    }

    @Override
    public int getDataSize(List<List<String>> data) {
        return data.size();
    }
    
    @Override
    public void setTitle(TableManager tableManager, int dataNum) {
        tableManager.setTitle("Total Lessons: " + dataNum);
    }
}
