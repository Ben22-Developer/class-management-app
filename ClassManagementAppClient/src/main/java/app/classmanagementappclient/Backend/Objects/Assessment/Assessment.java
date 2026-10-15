package app.classmanagementappclient.Backend.Objects.Assessment;

import java.sql.Date;

/**
 *
 * @author BEN
 */
public class Assessment 
{
    private final int lessonNum;
    private final String assessmentName;
    private final Date assessmentDate;
    private final int totalMarks;
    private int assessmentNum;

    public Assessment(int lessonNum, String assessmentName, Date assessmentDate, int totalMarks) {
        this.lessonNum = lessonNum;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.totalMarks = totalMarks;
    }

    public int getLessonNum() {
        return lessonNum;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public int getAssessmentNum() {
        return assessmentNum;
    }

    public void setAssessmentNum(int assessmentNum) {
        this.assessmentNum = assessmentNum;
    }

    @Override
    public String toString() {
        return "Assessment{" + "lessonNum=" + lessonNum + ", assessmentName=" + assessmentName + ", assessmentDate=" + assessmentDate + ", totalMarks=" + totalMarks + '}';
    }
}
