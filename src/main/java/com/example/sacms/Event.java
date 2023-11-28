package com.example.sacms;

public class Event {

    private int eventID;

    private String eventName;

    private String advisorName;

    private String dateofevent;

    private String venue;

    private String clubName;



    public Event(int eventID, String eventName, String advisorName, String dateofevent, String venue, String clubName) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.advisorName = advisorName;
        this.dateofevent = dateofevent;
        this.venue = venue;
        this.clubName = clubName;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getDateofevent() {
        return dateofevent;
    }

    public void setDateofevent(String dateofevent) {
        this.dateofevent = dateofevent;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
