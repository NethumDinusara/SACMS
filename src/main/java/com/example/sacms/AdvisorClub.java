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



/*    public static void state(){
        if (AdvisorAttendance.state.equals("1")){
            advisorName.setText("Wellalage");
        }
        else if(AdvisorAttendance.state.equals("2")){
            advisorName.setText("Rodrigo");
        } else if (AdvisorAttendance.state.equals("3")) {
            advisorName.setText("Gomes");
        }
        else{
            advisorName.setText("Wanniarachchi");
        }
    }*/


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

    public void onAttendance(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SACMS.class.getResource("AdAttendance.fxml"));
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
