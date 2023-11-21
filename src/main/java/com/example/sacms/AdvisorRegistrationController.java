package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

public class AdvisorRegistrationController {

    @FXML
    private TextField Email;

    @FXML
    private TextField Number;

    @FXML
    private TextField LastName;

    @FXML
    private TextField Password;

    @FXML
    private TextField UserName;

    @FXML
    private TextField firstName;

    @FXML
    private TextField TeacherID;

    public void AdvisorMain(ActionEvent actionEvent)throws Exception {
        if (validateFields()){
            Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Advisor.fxml")));
            MainStage.setScene(new Scene(root));
            MainStage.setTitle("Member");
        }

    }

    private boolean validateFields() {
        if (firstName.getText().isEmpty()) {
            showAlert("First name is required.");
            return false;
        }

        if (LastName.getText().isEmpty()) {
            showAlert("Last name is required.");
            return false;
        }

        if (TeacherID.getText().isEmpty()){
            showAlert("Teacher ID is required.");
            return false;
        }

        if (Number.getText().isEmpty()) {
            showAlert("Phone number is required.");
            return false;
        }


        if (UserName.getText().isEmpty()) {
            showAlert("Username is required.");
            return false;
        }

        if (Password.getText().isEmpty()) {
            showAlert("Password is required.");
            return false;
        }

        if (Email.getText().isEmpty()) {
            showAlert("Email is required.");
            return false;
        } else if (!Email.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Invalid email format.");
            return false;
        }



        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please correct the following error:");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }
}
