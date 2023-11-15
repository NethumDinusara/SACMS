package com.example.sacms;

public class Club {

    private String clubName;

    private String clubDescription;

    private String advisorName;

    private String presidentName;

    public Club (String clubDescription, String clubName, String advisorName, String presidentName){
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
