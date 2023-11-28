package com.example.sacms;

import javafx.scene.control.Button;

import java.sql.Date;

public class Club {

    private String clubName;

    private String clubDescription;

    private String advisorName;

    private String presidentName;

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


    public Club (String clubName, String clubDescription, String advisorName, String presidentName){
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.advisorName = advisorName;
        this.presidentName = presidentName;

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

    public String getPresidentName() {
        return presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName;
    }
}
