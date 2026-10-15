package app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.SubClasses;

import app.classmanagementappclient.Medium.ClassLesson.ClassLessonInsert.Proxy.InsertClassLessonProxyV1;
import app.classmanagementappclient.Medium.ClassLesson.ClassLessonMedium;
import app.classmanagementappclient.Backend.Objects.Lesson.ClassLesson;
import app.classmanagementappclient.Frontend.Common.ShowReport.ShowReport;
import app.classmanagementappclient.Frontend.FrontEndUtility;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDescendants.ClassesWithLessonsTable;
import app.classmanagementappclient.Frontend.MyJTable.MyJTableDialogs.ViewTables.ViewTables;
import app.classmanagementappclient.Frontend.MyJTable.TableDisplay;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.EditableTableType;
import app.classmanagementappclient.Frontend.MyJTable.TableStructure.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ViewClassLessonsDialog extends ViewTables {

    @Override
    public void display(List<List<String>> data) {
        
        TableDisplay tableDisplay = new TableDisplay();
        Table table = Table.getTable(EditableTableType.IsJustViewTable, true, false, "", List.of("","No", "Lessons"), List.of(2), data);
        JButton submitBtn = getSubmitBtn(table);
        JButton selectAllBtn =  new JButton("Select All");
        JButton selectNone =  new JButton("Select None");
        JButton removeSelectedBtn = getRemoveSelectedBtn(table);
        JComponent[] components = new JComponent[] { selectAllBtn, selectNone, removeSelectedBtn, submitBtn };
        List<String> values = super.getSelectedRowData();
        String title = values.get(0) + "/ " + values.get(1) + "/ Year " + values.get(2) + "/ Term " + values.get(3) + ", Lessons";
        
        commonPropertiesSetup(new JButton[] { selectAllBtn, selectNone }, components, tableDisplay, table, title);
    }

    @Override
    public List<List<String>> getData(int classTermNum) {
        
        List<String> lessons = new ClassLessonMedium().getAllLessonsInTerm(classTermNum);
        List<List<String>> lessons_num = new ArrayList<>();
        
        for (int i = 0; i < lessons.size(); i++) {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf( i + 1));
            list.add(lessons.get(i));
            lessons_num.add(list);
        }
        return lessons_num;
    }

    @Override
    public void showErrMsg() {
        new FrontEndUtility().runShowMsgJOptionPaneSwingWorker("This class might have been deleted or lessons in it have been deleted or edited\nPlease consider re-loading the table", "Informational Message", JOptionPane.INFORMATION_MESSAGE);    
    }
    
    private JButton getSubmitBtn (Table table) {
        
        JButton button = new JButton ("Submit");
        
        button.addActionListener(l -> {
            
            var classesWithLessonsTable = (ClassesWithLessonsTable) super.getTable();            
            List<List<String>> data = table.getOnViewCheckedRowsData();
            int classTermNum = classesWithLessonsTable.getClassTermNum();
            StringBuilder builder = new StringBuilder();
            
            for (List<String> lessonNames : data) {
                
                for (String lessonName : lessonNames) {
                    
                    ClassLesson classLesson = new ClassLesson(lessonName);
                    classLesson.setClassTermNum(classTermNum);
                    var proxy = new InsertClassLessonProxyV1();
                    var medium = new ClassLessonMedium(classLesson);
                    medium.insertClassLesson(proxy);
                    if (proxy.getIsInserted() == 0)
                        builder.append(medium.getMsg());
                }
            }
            table.removeAllTableCheckedRows();
            if (!data.isEmpty())
                new ShowReport().showReport(builder.toString(), "Lessons inserted successfully!\nPress reload to view changes");
        });
        return button;
    }    
}
