use classmanagementappclient;

CREATE USER IF NOT EXISTS 'teacherUser'@'localHost' IDENTIFIED BY '12345';

CREATE VIEW teacherPersonalData AS SELECT teacherNum, teacherName, TeacherPhoneNumber, teacherEmail, teacherpin FROM teacher;

CREATE VIEW teacherMessageView AS SELECT id, RecordedAt, TeacherNum, Message FROM TeacherMessage;

CREATE VIEW schoolData AS SELECT schoolNum, schoolName, teacherNum FROM school;

CREATE VIEW classData AS SELECT classNum, className, schoolNum FROM class;

CREATE VIEW classYearData AS SELECT classYearNum, year, classNum FROM classYear;

CREATE VIEW classTermData AS SELECT classTermNum, term, classYearNum FROM classTerm;

CREATE VIEW classLessonData AS SELECT lessonNum, lessonName, classTermNum FROM classLesson;

CREATE VIEW studentPersonalData AS SELECT studentNum, studentID, studentName, TeacherNum FROM student;

CREATE VIEW studentClassData AS SELECT studentClassNum, studentNum, classTermNum FROM studentClass;

CREATE VIEW attendanceData AS SELECT AttendanceNum, lessonNum, attendanceDate, recordedAt FROM attendance;

CREATE VIEW studentAttendanceRecordData AS SELECT attendanceNum, studentClassNum FROM studentAttendanceRecord;

CREATE VIEW assessmentData AS SELECT AssessmentNum, LessonNum, AssessmentName, AssessmentDate, TotalMarks FROM assessment;

CREATE VIEW studentAssessmentRecordData AS SELECT AssessmentNum, StudentClassNum, studentMarks FROM studentAssessmentRecord;

CREATE VIEW Teacher_BotChatView AS SELECT id, chatID, content, type, timestamp  FROM Teacher_BotChat;

CREATE VIEW chatUUIDView AS SELECT id, uuid, chatName, teacherNum  FROM chatUUID;

CREATE VIEW teacher_apikeyView AS SELECT teacherNum, apiKey  FROM teacher_apikey;


GRANT INSERT, SELECT, UPDATE, DELETE ON teacherPersonalData TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, DELETE ON teacherMessageView TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON schoolData TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON classData TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON classYearData TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON classTermData TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON classLessonData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON studentPersonalData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON studentClassData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON attendanceData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, DELETE ON studentAttendanceRecordData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON  assessmentData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON  studentAssessmentRecordData TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON  Teacher_BotChatView TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON chatUUIDView TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON  teacher_apikeyView TO 'teacherUser'@'localHost';