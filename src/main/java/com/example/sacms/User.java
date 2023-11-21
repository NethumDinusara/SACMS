package com.example.sacms;


import java.sql.Date;
import java.sql.SQLRecoverableException;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;

    // Parameterized constructor
    public User(String firstName, String lastName, String phoneNumber, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



class Member extends User {
    private Date dateOfBirth;
    private String gender;
    private String grade;
    private String studentId;

    // Parameterized constructor
    public Member(String firstName, String lastName, String phoneNumber, String username, String email, String password, Date dateOfBirth, String grade, String gender, String studentId) {
        super(firstName, lastName, phoneNumber, username, email, password);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.grade = grade;
        this.studentId = studentId;
    }

    //getters and setters
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


class Advisor extends User{
    private String teacherId;

    //parameterized constructor
    public Advisor(String firstName, String lastName, String phoneNumber, String username, String email, String password, String teacherId) {
        super(firstName, lastName, phoneNumber, username, email, password);
        this.teacherId=teacherId;
    }

    //getters and setters
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
