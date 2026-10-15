package app.classmanagementappclient.Backend.Objects.Attendance;

import java.sql.Date;

/**
 *
 * @author BEN
 */
public class Attendance 
{
    private final Date date;
    private final int lessonNum;

    public Attendance(Date date, int lessonNum) {
        this.date = date;
        this.lessonNum = lessonNum;
    }

    public Date getDate() {
        return date;
    }

    public int getLessonNum() {
        return lessonNum;
    }

    @Override
    public String toString() {
        return "Attendance{" + "date=" + date + ", lessonNum=" + lessonNum + '}';
    }
}
