package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AdvisorSchedule {

    @FXML
    public TextField clubName;

    @FXML
    public TextField eventVenue;


    @FXML
    public Label label1;

    @FXML
    public TextField eventName;

    @FXML
    public TextField advisorName;
    public Label clubNameError;
    public Label eventNameError;

    @FXML
    public Label venueError;

    @FXML
    public Label advNameError;

    @FXML
    public Label meetingTopicError;

    @FXML
    public Label meetingClubNameError;

    @FXML
    public Label meetingAdvisorError;

    @FXML
    public Label meetingDurationError;

    @FXML
    public TextField meetingTopic;

    @FXML
    public TextField nameClub;

    @FXML
    public TextField meetingAdvisor;

    @FXML
    public TextField meetDuration;

    @FXML
    private AnchorPane Schedule;

    @FXML
    public TextField activityName;

    @FXML
    public Label activityNameError;

    @FXML
    public TextField activityDate;

    @FXML
    public Label activityDateError;

    @FXML
    public TextField activityVenue;
    @FXML
    public Label activityVenueError;

    @FXML
    public TextField eventDate;

    @FXML
    public Label eDateError;

    @FXML
    public TextField meetingDate;

    @FXML
    public Label meetingDateError;

    @FXML
    public TextField clubNameAct;

    public Label clubNameActError;

    public TextField activityDescription;

    public Label actDescriptionError;
    public TextField eventTime;
    public Label eventTimeError;


    public boolean clubNameVar;
    public boolean eventNameVar;
    public boolean advNameVar;
    public boolean eventVenueVar;
    public boolean eventDateVar;
    public boolean eventTimeVar;
    public boolean clubValidate;

    private final Database database = new Database();

    public void createEvent(ActionEvent actionEvent) throws IOException, SQLException {
        Event obj = new Event();
        obj.setcName(clubName.getText());
        if (obj.getcName().equals("")){
            clubNameError.setText("Must Fill All Fields!");
            clubNameVar = false;
        } else {
            clubNameError.setText("");
            // Check if the entered club name exists in the club table
            if (!database.isClubNameExists(clubName.getText())) {
                showAlert("The entered club name does not exist in the club list. Please enter a valid club name.");
                clubNameVar = false;  // Update the variable to indicate an error
                return;  // Stop the method execution
            }
            clubNameVar = true;  // Only execute this line if the club name is valid

        }
        obj.seteName(eventName.getText());
        if(obj.geteName().equals("")){
            eventNameError.setText("Must Fill All Fields!");
            eventNameVar = false;
        }else {
            eventNameError.setText("");
            eventNameVar = true;
        }
        obj.setAdvName(advisorName.getText());
        if(obj.getAdvName().equals("")){
            advNameError.setText("Must Fill All Fields!");
            advNameVar = false;
        } else {
            advNameError.setText("");
            advNameVar = true;
        }
        obj.setVenue(eventVenue.getText());
        if(obj.getVenue().equals("")){
            venueError.setText("Must Fill All Fields!");
            eventVenueVar = false;
        }else {
            venueError.setText("");
            eventVenueVar = true;
        }
        obj.seteDate(eventDate.getText());
        if(obj.geteDate().equals("")){
            eDateError.setText("Must Fill All Fields!");
            eventDateVar = false;
        }else {
            eDateError.setText("");
            eventDateVar = true;
        }
        obj.seteTime(eventTime.getText());
        if(obj.geteTime().equals("")){
            eventTimeError.setText("Must Fill All Fields!");
            eventTimeVar = false;
        } else {
            eventTimeError.setText("");
            eventTimeVar = true;
        }



        if (clubNameVar && eventNameVar && advNameVar && eventVenueVar && eventDateVar && eventTimeVar) {
            Event e1 = new Event(
                    clubName.getText(),
                    eventName.getText(),
                    advisorName.getText(),
                    eventVenue.getText(),
                    eventDate.getText(),
                    eventTime.getText()
            );
            //Register the member in the database
            String event;
            if (database.storeAdvisorEventData(e1)) {
               // If registration is successful, proceed to the Member.fxml scene
               Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
               Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Schedule_event.fxml")));
               MainStage.setScene(new Scene(root));
               MainStage.setTitle("Schedule Event");
           } else {
               showAlert("Event Creation is failed. Please fill all fields and try again.");
           }
        }

    }

    public boolean meetingTopicVar;
    public boolean meetingClubNameVar;
    public boolean meetingAdvisorVar;
    public boolean meetingDurationVar;
    public boolean meetingDateVar;

    public void meetingCreate(ActionEvent actionEvent) throws IOException, SQLException {
        Meetings obj = new Meetings();
        obj.setMeetingTopic(meetingTopic.getText());
        if (obj.getMeetingTopic().isEmpty()) {
            meetingTopicError.setText("Must Fill All Fields!");
            meetingTopicVar= false;
        } else {
            meetingTopicError.setText(""); // Clear the error message
            meetingTopicVar = true;
        }

        obj.setMeetingClubName(nameClub.getText());
        if(obj.getMeetingClubName().isEmpty()) {
            meetingClubNameError.setText("Must Fill All Fields!");
            meetingClubNameVar = false;
        } else {
            clubNameError.setText("");
            // Check if the entered club name exists in the club table
            if (!database.isClubNameExists(nameClub.getText())) {
                showAlert("The entered club name does not exist in the club list. Please enter a valid club name.");
                meetingClubNameVar = false;  // Update the variable to indicate an error
                return;  // Stop the method execution
            }
            meetingClubNameVar = true;  // Only execute this line if the club name is valid

        }
        obj.setMeetingAdvisorName(meetingAdvisor.getText());
        if(obj.getMeetingAdvisorName().isEmpty()){
            meetingAdvisorError.setText("Must Fill All Fields!");
            meetingAdvisorVar = false;
        } else {
            meetingAdvisorError.setText("");
            meetingAdvisorVar = true;
        }

        obj.setMeetingDuration(meetDuration.getText());
        if(obj.getMeetingDuration().isEmpty()){
            meetingDurationError.setText("Must Fill All Fields!");
            meetingDurationVar = false;
        } else {
            meetingDurationError.setText("");
            meetingDurationVar = true;
        }

        obj.setMeetingDate(meetingDate.getText());
        if(obj.getMeetingDate().isEmpty()){
            meetingDateError.setText("Must Fill All Fields!");
            meetingDateVar = false;
        } else {
            meetingDateError.setText("");
            meetingDateVar = true;
        }

        if (meetingTopicVar == true && meetingClubNameVar == true && meetingAdvisorVar == true && meetingDurationVar == true && meetingDateVar == true){
            Event e1 = new Event(
                    meetingTopic.getText(),
                    nameClub.getText(),
                    meetingAdvisor.getText(),
                    meetDuration.getText(),
                    meetingDate.getText()
            );


            Meetings meetings;
            if (database.storeAdvisorMeetingData(obj)) {
                // If registration is successful, proceed to the Member.fxml scene
                Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Schedule_event.fxml")));
                MainStage.setScene(new Scene(root));
                MainStage.setTitle("Schedule Event");
            } else {
                showAlert("Meeting Creation is failed. Please fill all fields and try again.");
            }

        }

    }



    public boolean activityNameVar;
    public boolean activityDateVar;
    public boolean activityVenueVar;
    public boolean clubNameActVar;
    public boolean activityDescriptionVar;



    public void createActivity(ActionEvent actionEvent) throws SQLException, IOException {

        Activity obj = new Activity();
        obj.setActName(activityName.getText());
        if(obj.getActName().isEmpty()){
            activityNameError.setText("Must Fill All Fields!");
            activityNameVar = false;
        }else {
            activityNameError.setText("");
            activityNameVar = true;
        }
        obj.setActDate(activityDate.getText());
        if(obj.getActDate().isEmpty()){
            activityDateError.setText("Must Fill All Fields!");
            activityDateVar = false;
        }else {
            activityDateError.setText("");
            activityDateVar = true;
        }
        obj.setActVenue(activityVenue.getText());
        if(obj.getActVenue().isEmpty()){
            activityVenueError.setText("Must Fill All Fields!");
            activityVenueVar = false;
        }else {
            activityVenueError.setText("");
            activityVenueVar = true;
        }
        obj.setActClubName(clubNameAct.getText());
        if(obj.getActClubName().isEmpty()){
            clubNameActError.setText("Must Fill All Fields!");
            clubNameActVar = false;
        }else {
            clubNameError.setText("");
            // Check if the entered club name exists in the club table
            if (!database.isClubNameExists(clubNameAct.getText())) {
                showAlert("The entered club name does not exist in the club list. Please enter a valid club name.");
                clubNameActVar = false;  // Update the variable to indicate an error
                return;  // Stop the method execution
            }
            clubNameActVar = true;  // Only execute this line if the club name is valid

        }
        obj.setActDescription(activityDescription.getText());
        if (obj.getActDescription().isEmpty()){
            actDescriptionError.setText("Must Fill All Fields!");
            activityDescriptionVar = false;
        }else {
            actDescriptionError.setText("");
            activityDescriptionVar = true;
        }

        if (activityNameVar == true && activityDateVar == true && activityVenueVar == true && clubNameActVar == true && activityDescriptionVar == true) {
            Event e1 = new Event(
                    activityName.getText(),
                    activityDate.getText(),
                    activityVenue.getText(),
                    clubNameAct.getText(),
                    activityDescription.getText()
            );

            Activity activity;
            if (database.storeAdvisorActivityData(obj)) {
                Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Schedule_event.fxml")));
                MainStage.setScene(new Scene(root));
                MainStage.setTitle("Schedule Event");
            } else {
                showAlert("Meeting Creation is failed. Please fill all fields and try again.");
            }
        }
    }


        public void onBackBottonClick (ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 900, 550);
            stage.setTitle("Schedule");
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) Schedule.getScene().getWindow();
            currentStage.close();

        }


        public void goBack (ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 900, 550);
            stage.setTitle("Schedule");
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) Schedule.getScene().getWindow();
            currentStage.close();
        }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please correct the following error:");
        alert.setContentText(message);
        alert.showAndWait();
    }


    }
