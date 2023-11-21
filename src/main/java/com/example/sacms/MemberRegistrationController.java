package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Date;
import java.util.Objects;



public class MemberRegistrationController {

    @FXML
    private DatePicker DOB;

    @FXML
    private TextField Email;

    @FXML
    private TextField Grade;

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
    private ToggleGroup Gender;

    @FXML
    private TextField StudentID;

    private final Database database = new Database();




    @FXML
    public void MemberMain(ActionEvent actionEvent)throws Exception {

        if (validateFields()) {
            // Create a Member object with the provided data
            Member member = new Member(
                    firstName.getText(),
                    LastName.getText(),
                    Number.getText(),
                    UserName.getText(),
                    Email.getText(),
                    Password.getText(),
                    Date.valueOf(DOB.getValue()),
                    Grade.getText(),
                    getSelectedGender(),
                    StudentID.getText()
            );

            // Register the member in the database
            if (database.registerUser(member, "member")) {
                // If registration is successful, proceed to the Member.fxml scene
                Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MemberLogin.fxml")));
                MainStage.setScene(new Scene(root));
                MainStage.setTitle("Member Login");
            } else {
                showAlert("Registration failed. Please try again.");
            }
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

        if (DOB.getValue() == null) {
            showAlert("Date of Birth is required.");
            return false;
        }

        if (Grade.getText().isEmpty()) {
            showAlert("Grade is required.");
            return false;
        }else if(!Grade.getText().matches("[1-9]|1[0-2]")){
            showAlert("Grade should be between 1 and 12");
            return false;
        }

        if (Gender.getSelectedToggle() == null) {
            showAlert("Gender is required.");
            return false;
        }

        if (StudentID.getText().isEmpty()) {
            showAlert("Student ID is required.");
            return false;
        } else if (database.isUserIdExists(StudentID.getText(),"member")) {
            showAlert("Your student ID already exists. Please check");
            return false;
        }

        if (Number.getText().isEmpty()) {
            showAlert("Phone number is required.");
            return false;
        }else if(Number.getText().length()!=10 || !Number.getText().matches("\\d+")){
            showAlert("Phone number should be a 10-digit number");
            return false;
        }

        if (UserName.getText().isEmpty()) {
            showAlert("Username is required.");
            return false;
        } else if (!UserName.getText().matches("M\\d*")) {
            showAlert("Invalid Username format.");
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

    private String getSelectedGender() {
        if (Gender.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) Gender.getSelectedToggle();
            return selectedRadioButton.getText();
        }
        return null;
    }

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

}