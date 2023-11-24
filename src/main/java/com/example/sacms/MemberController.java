package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MemberController{

    @FXML
    private Button btnClubs;

    @FXML
    private Button btnEvents;

    @FXML
    private Button btnProfile;

    @FXML
    private AnchorPane Events;

    @FXML
    private AnchorPane yourClubs;

    @FXML
    private AnchorPane yourProfile;

    @FXML
    private Label userFName;

    @FXML
    private Label userLName;

    @FXML
    private Label email;

    @FXML
    private Label grade;

    @FXML
    private Label phoneNumber;

    private final Database database = new Database();
    private String username;


    public void SwitchForm(ActionEvent event){
        if(event.getSource() == btnProfile){
            yourProfile.setVisible(true);
            yourClubs.setVisible(false);
            Events.setVisible(false);


            // Set member data when switching to the profile view
            Member member = database.getMemberData(username);
            if (member != null) {
                setMemberData(member);
            }
        } else if (event.getSource() == btnClubs){
            yourProfile.setVisible(false);
            yourClubs.setVisible(true);
            Events.setVisible(false);
        }else if (event.getSource() == btnEvents) {
            yourProfile.setVisible(false);
            yourClubs.setVisible(false);
            Events.setVisible(true);
        }
    }

    //Your Profile

    //set Data to the text filed
    public void setUsername(String username) {
        this.username = username;
    }



    public void setMemberData(Member member) {
        userFName.setText(member.getFirstName());
        userLName.setText(member.getLastName());
        email.setText(member.getEmail());
        grade.setText("grade " + member.getGrade());
        phoneNumber.setText(member.getPhoneNumber());
    }


    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }



}
