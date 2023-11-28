package com.example.sacms;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

import java.sql.Date;


public class Club {

    private String clubName;

    private String clubDescription;

    private static String advisorName;

    private String advisorPhoneNumber;

    private Date joinDate;

    private Button quitButton;


    public String getAdvisorPhoneNumber() {
        return advisorPhoneNumber;
    }

    public void setAdvisorPhoneNumber(String advisorPhoneNumber) {
        this.advisorPhoneNumber = advisorPhoneNumber;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public void setQuitButton(Button quitButton) {
        this.quitButton = quitButton;
    }

    private int clubID;


    public Club(String clubName, String clubDescription, String advisorName) {
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.advisorName = advisorName;

    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    // Getter for TableView
    public StringProperty clubNameProperty() {
        return new SimpleStringProperty(clubName);
    }

    public StringProperty clubDescriptionProperty() {
        return new SimpleStringProperty(clubDescription);
    }

    public StringProperty advisorNameProperty() {
        return new SimpleStringProperty(advisorName);
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public static class ManageClub extends Club {

        private String advisorID;


        public ManageClub(String clubName, String clubDescription,String advisorName, String advisorID) {
            super(clubName, clubDescription, advisorName);
            this.advisorID = advisorID;
        }


        public String getAdvisorID() {
            return advisorID;
        }

        public void setAdvisorID(String advisorID) {
            this.advisorID = advisorID;
        }
    }
}
