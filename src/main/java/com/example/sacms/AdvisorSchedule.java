package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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



    public void createEvent(ActionEvent actionEvent){
        event obj = new event();
        obj.setcName(clubName.getText());
        if (obj.getcName().equals("")){
            clubNameError.setText("Must Fill All Fields!");
        }
        obj.seteName(eventName.getText());
        if(obj.geteName().equals("")){
            eventNameError.setText("Must Fill All Fields!");
        }
        obj.setAdvName(advisorName.getText());
        if(obj.getAdvName().equals("")){
            advNameError.setText("Must Fill All Fields!");
        }
        obj.setVenue(eventVenue.getText());
        if(obj.getVenue().equals("")){
            venueError.setText("Must Fill All Fields!");

        }



    }

    public void meetingCreate(ActionEvent actionEvent) {
        Meetings obj = new Meetings();
        obj.setMeetingTopic(meetingTopic.getText());
        if (obj.getMeetingTopic().isEmpty()) {
            meetingTopicError.setText("Must Fill All Fields!");
        } else {
            meetingTopicError.setText(""); // Clear the error message
        }

        obj.setMeetingClubName(nameClub.getText());
        if(obj.getMeetingClubName().isEmpty()) {
            meetingClubNameError.setText("Must Fill All Fields!");
        } else {
            meetingClubNameError.setText("");
        }

        obj.setMeetingAdvisorName(meetingAdvisor.getText());
        if(obj.getMeetingAdvisorName().isEmpty()){
            meetingAdvisorError.setText("Must Fill All Fields!");
        } else {
            meetingAdvisorError.setText("");
        }

        obj.setMeetingDuration(meetDuration.getText());
        if(obj.getMeetingDuration().isEmpty()){
            meetingDurationError.setText("Must Fill All Fields!");
        } else {
            meetingDurationError.setText("");
        }
    }

    public void createActivity(ActionEvent actionEvent) {
    }

    public void onBackBottonClick(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) Schedule.getScene().getWindow();
        currentStage.close();




    }

    public void goBack(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) Schedule.getScene().getWindow();
        currentStage.close();
    }


}
