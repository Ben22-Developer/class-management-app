package app.classmanagementappclient.Backend.Objects.AssessmentRecord;

/**
 *
 * @author BEN
 */
public class AssessmentRecord {
    private final int assessmentNum;
    private final int studentClassNum;
    private final double studentMarks;

    public AssessmentRecord(int assessmentNum, int studentClassNum, double studentMarks) {
        this.assessmentNum = assessmentNum;
        this.studentClassNum = studentClassNum;
        this.studentMarks = studentMarks;
    }

    public int getAssessmentNum() {
        return assessmentNum;
    }

    public int getStudentClassNum() {
        return studentClassNum;
    }

    public double getStudentMarks() {
        return studentMarks;
    }

    @Override
    public String toString() {
        return "StudentAssessmentRecord{" + "assessmentNum=" + assessmentNum + ", studentClassNum=" + studentClassNum + ", studentMarks=" + studentMarks + '}';
    }
}
