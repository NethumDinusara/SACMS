package com.example.sacms;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.Date;

public class Member extends User {
    private Date dateOfBirth;
    private String gender;
    private String grade;
    private String studentId;
    private BooleanProperty attendance;



    public Member(String firstName, String lastName, String phoneNumber, String username, String email, String password, Date dateOfBirth, String grade, String gender, String studentId, boolean attendance) {
        super(firstName, lastName, phoneNumber, username, email, password);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.grade = grade;
        this.studentId = studentId;
        this.attendance = new SimpleBooleanProperty(attendance);
    }

    public Member(String studentId, String firstName, String lastName, String grade) {
        super(firstName, lastName);
        this.grade = grade;
        this.studentId = studentId;
        this.attendance = new SimpleBooleanProperty();
    }


    public Member(String firstName, String lastName, String phoneNumber, String username, String email, String password, Date dateOfBirth, String gender, String grade, String studentId) {
        super(firstName, lastName, phoneNumber, username, email, password);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.grade = grade;
        this.studentId = studentId;
    }

    public BooleanProperty attendanceProperty() {
        return attendance;
    }

    //Update attendance status
    public void setAttendanceStatus(boolean status){
        this.attendance.set(status);
    }
    public boolean isAttendance() {
        return attendance.get();
    }

    public void setAttendance(boolean attendance) {
        this.attendance.set(attendance);
    }

    public void setAttendanceProperty(BooleanProperty attendance){
        this.attendance = attendance;
    }
    public BooleanProperty getAttendanceProperty() {
        return attendance;
    }

    // Getters and Setters
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
