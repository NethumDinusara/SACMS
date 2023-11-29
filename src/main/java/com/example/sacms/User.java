package com.example.sacms;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.Date;

//main user class (super class)
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

    public User(String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
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


//Advisor class (subclass)
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
