package com.example.sacms;

public class Event {
    // Attributes to store information about the event

    private int eventID;
    private String clubName;     // Club Name
    private String eventName;     // Event Name
    private String advisorName;   // Advisor Name
    private String venue;     // Venue
    private String dateofevent;     // Event Date
    private String eventTime;     // Event Time


    // Constructor to initialize all attributes when both date and time are provided
    public Event(String clubName, String eventName, String advisorName, String venue, String dateofevent, String eventTime) {
        this.clubName = clubName;
        this.eventName = eventName;
        this.advisorName = advisorName;
        this.venue = venue;
        this.dateofevent = dateofevent;
        this.eventTime = eventTime;
    }

    public Event(int eventID, String eventName, String advisorName, String dateofevent, String venue, String clubName) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.advisorName = advisorName;
        this.dateofevent = dateofevent;
        this.venue = venue;
        this.clubName = clubName;
    }



    // Getter and setter for the event time
    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    // Constructor to initialize attributes when only date is provided
    public Event(String clubName, String eventName, String advisorName, String venue, String dateofevent) {
        this.clubName = clubName;
        this.eventName = eventName;
        this.advisorName = advisorName;
        this.venue = venue;
        this.dateofevent = dateofevent;
    }
    public Event(){   //// Default constructor

    }

    public Event(int eventID, String eventName, String clubName) {
        this.eventID = eventID;
        this.clubName = clubName;
        this.eventName = eventName;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    // Additional getters and setters for other attributes
    public String getDateofevent() {
        return dateofevent;
    }

    public void setDateofevent(String dateofevent) {
        this.dateofevent = dateofevent;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
