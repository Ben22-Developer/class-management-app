package app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.ByOtherClassInsert;

import app.classmanagementappclient.Frontend.InputWindows.EventHandlers.AddNewStudents.StudentInsertParent;

/**
 *
 * @author BEN
 */
public class StudentInsertFromOtherClass extends StudentInsertParent {

    private final int classTermNum;

    public StudentInsertFromOtherClass(int classTermNum) {
        this.classTermNum = classTermNum;
    }
    
    @Override
    protected int getClassTermNum() {
        return classTermNum;
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "\nU can click reload button to view changes!";
    }
}
