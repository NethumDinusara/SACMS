package com.example.sacms;

public class AttendanceRecord {

    private int StudentID;
    private String FirstName;
    private String LastName;
    private int grade;
    private int EventID;

    public AttendanceRecord(int studentID, String firstName, String lastName, int grade, int eventID, String status) {
        this.StudentID = studentID;
        this.FirstName = firstName; // Set to empty string if null
        this.LastName = lastName;
        this.grade = grade;
        this.EventID = eventID;
    }


    public AttendanceRecord(String studentId, String firstName, String lastName, String grade, int currentEventID, String attendanceStatus) {
        this.StudentID= Integer.parseInt(studentId);
        this.FirstName=firstName;
        this.LastName=lastName;
        this.grade= Integer.parseInt(grade);
        this.EventID=currentEventID;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int studentID) {
        StudentID = studentID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int eventID) {
        EventID = eventID;
    }

    public int getStudentId() {
        return StudentID;
    }
}

