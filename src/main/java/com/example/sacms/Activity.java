package com.example.sacms;

public class Activity {
    String actName;
    String actDate;
    String actVenue;
    String actClubName;

    String actDescription;

    public Activity(String actName, String actDate, String actVenue, String actClubName, String actDescription) {
        this.actName = actName;
        this.actDate = actDate;
        this.actVenue = actVenue;
        this.actClubName = actClubName;
        this.actDescription = actDescription;
    }

    public Activity() {

    }

    public String getActDescription() {
        return actDescription;
    }

    public void setActDescription(String actDescription) {
        this.actDescription = actDescription;
    }

    public String getActClubName() {
        return actClubName;
    }

    public void setActClubName(String actClubName) {
        this.actClubName = actClubName;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActDate() {
        return actDate;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public String getActVenue() {
        return actVenue;
    }

    public void setActVenue(String actVenue) {
        this.actVenue = actVenue;
    }
}
