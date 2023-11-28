package com.example.sacms;

public class event {
    // Attributes to store information about the event
    String cName;     // Club Name
    String eName;     // Event Name
    String advName;   // Advisor Name
    String Venue;     // Venue
    String eDate;     // Event Date
    String eTime;     // Event Time


    // Constructor to initialize all attributes when both date and time are provided
    public event(String cName, String eName, String advName, String venue, String eDate, String eTime) {
        this.cName = cName;
        this.eName = eName;
        this.advName = advName;
        Venue = venue;
        this.eDate = eDate;
        this.eTime = eTime;
    }

    // Getter and setter for the event time
    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    // Constructor to initialize attributes when only date is provided
    public event(String cName, String eName, String advName, String venue, String eDate) {
        this.cName = cName;
        this.eName = eName;
        this.advName = advName;
        Venue = venue;
        this.eDate = eDate;
    }
    public event(){   //// Default constructor

    }


    // Additional getters and setters for other attributes
    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }
}
