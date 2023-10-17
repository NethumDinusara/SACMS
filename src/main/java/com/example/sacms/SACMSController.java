package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.util.Objects;

public class SACMSController {


    @FXML
    //Go to driver details
    public void LoadMember(ActionEvent actionEvent)throws Exception{
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MemberLogin.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("Member Login");
    }

    @FXML
    //Go to driver details
    public void LoadAdvisor(ActionEvent actionEvent)throws Exception{
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdvisorLogin.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("Advisor Login");
    }

}
