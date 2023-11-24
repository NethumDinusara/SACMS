package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdvisorClub {

    @FXML
    private AnchorPane advisorClubANC;

    @FXML
    public static Label advisorName;

    private final Database database = new Database();


    private String username;



    public void initialize() {
        Advisor advisor = database.getAdvisorData(username);
        if (advisor != null) {
            setAdvisorData(advisor);
        }
    }

    public void onManageClubs(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) advisorClubANC.getScene().getWindow();
        currentStage.close();
    }


    public void onSearchButtonClick(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("AdvisorEvent.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) advisorClubANC.getScene().getWindow();
        currentStage.close();
    }

    public void setAdvisorData(Advisor advisor) {
        advisorName.setText(advisor.getFirstName() + " " + advisor.getLastName());
    }


    public void goBackTo(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("Advisor.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) advisorClubANC.getScene().getWindow();
        currentStage.close();
    }


}
