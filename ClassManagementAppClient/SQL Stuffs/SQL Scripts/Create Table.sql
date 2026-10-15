-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema classmanagementappclient
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema classmanagementappclient
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `classmanagementappclient` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `classmanagementappclient` ;

-- -----------------------------------------------------
-- Table `classmanagementappclient`.`Teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`Teacher` (
  `TeacherNum` INT NOT NULL AUTO_INCREMENT,
  `TeacherName` VARCHAR(45) NOT NULL,
  `TeacherPin` VARCHAR(100) NOT NULL,
  `TeacherPhoneNumber` VARCHAR(20) NOT NULL,
  `TeacherEmail` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`TeacherNum`),
  UNIQUE INDEX `TeacherEmail_UNIQUE` (`TeacherEmail` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`Student` (
  `StudentNum` INT NOT NULL AUTO_INCREMENT,
  `StudentName` VARCHAR(45) NOT NULL,
  `StudentID` VARCHAR(45) NOT NULL,
  `TeacherNum` INT NOT NULL,
  INDEX `fk_Student_Teacher1_idx` (`TeacherNum` ASC) VISIBLE,
  PRIMARY KEY (`StudentID`, `TeacherNum`),
  UNIQUE INDEX `StudentNum_UNIQUE` (`StudentNum` ASC) VISIBLE,
  CONSTRAINT `fk_Student_Teacher1`
    FOREIGN KEY (`TeacherNum`)
    REFERENCES `classmanagementappclient`.`Teacher` (`TeacherNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`School`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`School` (
  `SchoolNum` INT NOT NULL AUTO_INCREMENT,
  `SchoolName` VARCHAR(45) NOT NULL,
  `TeacherNum` INT NOT NULL,
  PRIMARY KEY (`SchoolName`, `TeacherNum`),
  INDEX `fk_School_Teacher1_idx` (`TeacherNum` ASC) VISIBLE,
  UNIQUE INDEX `SchoolNum_UNIQUE` (`SchoolNum` ASC) VISIBLE,
  CONSTRAINT `fk_School_Teacher1`
    FOREIGN KEY (`TeacherNum`)
    REFERENCES `classmanagementappclient`.`Teacher` (`TeacherNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`Class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`Class` (
  `ClassNum` INT NOT NULL AUTO_INCREMENT,
  `ClassName` VARCHAR(45) NOT NULL,
  `SchoolNum` INT NOT NULL,
  PRIMARY KEY (`ClassName`, `SchoolNum`),
  INDEX `fk_Class_School1_idx` (`SchoolNum` ASC) VISIBLE,
  UNIQUE INDEX `ClassNum_UNIQUE` (`ClassNum` ASC) VISIBLE,
  CONSTRAINT `fk_Class_School1`
    FOREIGN KEY (`SchoolNum`)
    REFERENCES `classmanagementappclient`.`School` (`SchoolNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`ClassYear`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`ClassYear` (
  `ClassYearNum` INT NOT NULL AUTO_INCREMENT,
  `Year` INT NOT NULL,
  `ClassNum` INT NOT NULL,
  INDEX `fk_ClassYear_Class1_idx` (`ClassNum` ASC) VISIBLE,
  PRIMARY KEY (`Year`, `ClassNum`),
  UNIQUE INDEX `ClassYearNum_UNIQUE` (`ClassYearNum` ASC) VISIBLE,
  CONSTRAINT `fk_ClassYear_Class1`
    FOREIGN KEY (`ClassNum`)
    REFERENCES `classmanagementappclient`.`Class` (`ClassNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`ClassTerm`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`ClassTerm` (
  `ClassTermNum` INT NOT NULL AUTO_INCREMENT,
  `Term` INT NOT NULL,
  `ClassYearNum` INT NOT NULL,
  PRIMARY KEY (`Term`, `ClassYearNum`),
  INDEX `fk_ClassTerm_ClassYear1_idx` (`ClassYearNum` ASC) VISIBLE,
  UNIQUE INDEX `ClassTermNum_UNIQUE` (`ClassTermNum` ASC) VISIBLE,
  CONSTRAINT `fk_ClassTerm_ClassYear1`
    FOREIGN KEY (`ClassYearNum`)
    REFERENCES `classmanagementappclient`.`ClassYear` (`ClassYearNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`ClassLesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`ClassLesson` (
  `classLessonNum` INT NOT NULL AUTO_INCREMENT,
  `LessonName` VARCHAR(45) NOT NULL,
  `ClassTermNum` INT NOT NULL,
  UNIQUE INDEX `LessonNum_UNIQUE` (`LessonNum` ASC) VISIBLE,
  INDEX `fk_Lesson_ClassTerm1_idx` (`ClassTermNum` ASC) VISIBLE,
  PRIMARY KEY (`LessonName`, `ClassTermNum`),
  CONSTRAINT `fk_Lesson_ClassTerm1`
    FOREIGN KEY (`ClassTermNum`)
    REFERENCES `classmanagementappclient`.`ClassTerm` (`ClassTermNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classmanagementappclient`.`StudentClass`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`StudentClass` (
  `StudentClassNum` INT NOT NULL AUTO_INCREMENT,
  `StudentNum` INT NOT NULL,
  `ClassTermNum` INT NOT NULL,
  INDEX `fk_StudentClass_ClassTerm1_idx` (`ClassTermNum` ASC) VISIBLE,
  UNIQUE INDEX `StudentClassNum_UNIQUE` (`StudentClassNum` ASC) VISIBLE,
  PRIMARY KEY (`StudentNum`, `ClassTermNum`),
  CONSTRAINT `fk_StudentClass_Student1`
    FOREIGN KEY (`StudentNum`)
    REFERENCES `classmanagementappclient`.`Student` (`StudentNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_StudentClass_ClassTerm1`
    FOREIGN KEY (`ClassTermNum`)
    REFERENCES `classmanagementappclient`.`ClassTerm` (`ClassTermNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`Attendance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`Attendance` (
  `AttendanceNum` INT NOT NULL AUTO_INCREMENT,
  `LessonNum` INT NOT NULL,
  `AttendanceDate` DATE NOT NULL,
  `recordedAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `fk_Attendance_Lesson1_idx` (`LessonNum` ASC) VISIBLE,
  UNIQUE INDEX `AttendanceNum_UNIQUE` (`AttendanceNum` ASC) VISIBLE,
  PRIMARY KEY (`LessonNum`, `AttendanceDate`, `recordedAt`),
  CONSTRAINT `fk_Attendance_Lesson1`
    FOREIGN KEY (`LessonNum`)
    REFERENCES `classmanagementappclient`.`ClassLesson` (`LessonNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`StudentAttendanceRecord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`StudentAttendanceRecord` (
  `AttendanceNum` INT NOT NULL,
  `StudentClassNum` INT NOT NULL,
  PRIMARY KEY (`AttendanceNum`, `StudentClassNum`),
  INDEX `fk_StudentAttendanceRecord_StudentClass1_idx` (`StudentClassNum` ASC) VISIBLE,
  CONSTRAINT `fk_StudentAttendance_Attendance1`
    FOREIGN KEY (`AttendanceNum`)
    REFERENCES `classmanagementappclient`.`Attendance` (`AttendanceNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_StudentAttendanceRecord_StudentClass1`
    FOREIGN KEY (`StudentClassNum`)
    REFERENCES `classmanagementappclient`.`StudentClass` (`StudentClassNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`Assessment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`Assessment` (
  `AssessmentNum` INT NOT NULL AUTO_INCREMENT,
  `LessonNum` INT NOT NULL,
  `AssessmentName` VARCHAR(45) NOT NULL,
  `AssessmentDate` DATE NOT NULL,
  `TotalMarks` INT NULL,
  PRIMARY KEY (`LessonNum`, `AssessmentName`, `AssessmentDate`),
  UNIQUE INDEX `AssessmentNum_UNIQUE` (`AssessmentNum` ASC) VISIBLE,
  CONSTRAINT `fk_Assessment_Lesson1`
    FOREIGN KEY (`LessonNum`)
    REFERENCES `classmanagementappclient`.`ClassLesson` (`LessonNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`StudentAssessmentRecord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`StudentAssessmentRecord` (
  `AssessmentNum` INT NOT NULL,
  `StudentClassNum` INT NOT NULL,
  `StudentMarks` DOUBLE NOT NULL,
  PRIMARY KEY (`AssessmentNum`, `StudentClassNum`),
  INDEX `fk_StudentAssessmentRecord_StudentClass1_idx` (`StudentClassNum` ASC) VISIBLE,
  CONSTRAINT `fk_StudentAssessmentRecord_Assessment1`
    FOREIGN KEY (`AssessmentNum`)
    REFERENCES `classmanagementappclient`.`Assessment` (`AssessmentNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_StudentAssessmentRecord_StudentClass1`
    FOREIGN KEY (`StudentClassNum`)
    REFERENCES `classmanagementappclient`.`StudentClass` (`StudentClassNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`Teacher_ApiKey`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`Teacher_ApiKey` (
  `TeacherNum` INT NOT NULL,
  `apiKey` VARCHAR(100) NOT NULL,
  INDEX `fk_table1_Teacher1_idx` (`TeacherNum` ASC) VISIBLE,
  PRIMARY KEY (`TeacherNum`),
  UNIQUE INDEX `apiNum_UNIQUE` (`apiKey` ASC) VISIBLE,
  CONSTRAINT `fk_table1_Teacher1`
    FOREIGN KEY (`TeacherNum`)
    REFERENCES `classmanagementappclient`.`Teacher` (`TeacherNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classmanagementappclient`.`chatUUID`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`chatUUID` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(45) NOT NULL,
  `chatName` VARCHAR(45) NOT NULL,
  `TeacherNum` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE,
  INDEX `fk_chatUUID_Teacher1_idx` (`TeacherNum` ASC) VISIBLE,
  UNIQUE INDEX `chatName_UNIQUE` (`chatName` ASC) VISIBLE,
  CONSTRAINT `fk_chatUUID_Teacher1`
    FOREIGN KEY (`TeacherNum`)
    REFERENCES `classmanagementappclient`.`Teacher` (`TeacherNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classmanagementappclient`.`teacher_botChat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`teacher_botChat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `chatID` INT NOT NULL,
  `content` TEXT NOT NULL,
  `type` ENUM('USER', 'BOT') NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `fk_user_botChats_chatUUID1_idx` (`chatID` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_botChats_chatUUID1`
    FOREIGN KEY (`chatID`)
    REFERENCES `classmanagementappclient`.`chatUUID` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classmanagementappclient`.`TeacherMessage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappclient`.`TeacherMessage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `RecordedAt` VARCHAR(45) NOT NULL,
  `TeacherNum` INT NOT NULL,
  `Message` TEXT NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`RecordedAt`, `TeacherNum`),
  INDEX `fk_table1_Teacher2_idx` (`TeacherNum` ASC) VISIBLE,
  CONSTRAINT `fk_table1_Teacher2`
    FOREIGN KEY (`TeacherNum`)
    REFERENCES `classmanagementappclient`.`Teacher` (`TeacherNum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


ALTER TABLE teacher AUTO_INCREMENT = 0;
ALTER TABLE student AUTO_INCREMENT = 0;
ALTER TABLE school AUTO_INCREMENT = 0;
ALTER TABLE class AUTO_INCREMENT = 0;
ALTER TABLE classYear AUTO_INCREMENT = 0;
ALTER TABLE classTerm AUTO_INCREMENT = 0;
ALTER TABLE classLesson AUTO_INCREMENT = 0;
ALTER TABLE studentClass AUTO_INCREMENT = 0;
ALTER TABLE attendance AUTO_INCREMENT = 0;
ALTER TABLE studentAttendanceRecord AUTO_INCREMENT = 0;
ALTER TABLE assessment AUTO_INCREMENT = 0;
ALTER TABLE StudentAssessmentRecord AUTO_INCREMENT = 0;
ALTER TABLE Teacher_ApiKey AUTO_INCREMENT = 0;
ALTER TABLE chatUUID AUTO_INCREMENT = 0;
ALTER TABLE teacher_botChat AUTO_INCREMENT = 0;
ALTER TABLE TeacherMessage AUTO_INCREMENT = 0;