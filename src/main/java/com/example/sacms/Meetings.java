package com.example.sacms;

public class Meetings {
    String meetingTopic;
    String meetingClubName;
    String meetingAdvisorName;
    String meetingDuration;
    String meetingDate;


    public Meetings(String meetingTopic, String meetingClubName, String meetingAdvisorName, String meetingDuration, String meetingDate) {
        this.meetingTopic = meetingTopic;
        this.meetingClubName = meetingClubName;
        this.meetingAdvisorName = meetingAdvisorName;
        this.meetingDuration = meetingDuration;
        this.meetingDate = meetingDate;
    }

    public Meetings(){

    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingTopic() {
        return meetingTopic;
    }

    public void setMeetingTopic(String meetingTopic) {
        this.meetingTopic = meetingTopic;
    }

    public String getMeetingClubName() {
        return meetingClubName;
    }

    public void setMeetingClubName(String meetingClubName) {
        this.meetingClubName = meetingClubName;
    }

    public String getMeetingAdvisorName() {
        return meetingAdvisorName;
    }

    public void setMeetingAdvisorName(String meetingAdvisorName) {
        this.meetingAdvisorName = meetingAdvisorName;
    }

    public String getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(String meetingDuration) {
        this.meetingDuration = meetingDuration;
    }
}
