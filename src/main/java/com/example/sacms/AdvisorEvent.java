package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdvisorEvent {


    @FXML
    private AnchorPane advisorEventANC;


    public void onManageClubs(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) advisorEventANC.getScene().getWindow();
        currentStage.close();
    }

    public void onAttendance(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("AdAttendance.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) advisorEventANC.getScene().getWindow();
        currentStage.close();
    }

    public void goBackTo(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("AdvisorClub.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) advisorEventANC.getScene().getWindow();
        currentStage.close();
    }
}
