package com.example.sacms;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;



public class MemberRegistrationController implements Initializable {

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
    private ListView<String> listView;

    @FXML
    private TextField Password;

    @FXML
    private TextField UserName;

    @FXML
    private TextField firstName;

    @FXML
    private ToggleGroup gender;

    @FXML
    private Label selection;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"Cricket","Swimming","Basketball","Football","Carom","Rugby","Chess","Athletic","Hockey","Golf"};
        listView.getItems().addAll(items);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
    }

    private void selectionChanged(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        String getSelectedItem = (selectedItems.isEmpty())?"No Selected Item":selectedItems.toString();
        selection.setText(getSelectedItem);

    }



    @FXML
    public void MemberMain(ActionEvent actionEvent)throws Exception {
        if (validateFields()){
            Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Member.fxml")));
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

        if (DOB.getValue() == null) {
            showAlert("Date of Birth is required.");
            return false;
        }

        if (Grade.getText().isEmpty()) {
            showAlert("Grade is required.");
            return false;
        }

        if (Number.getText().isEmpty()) {
            showAlert("Phone number is required.");
            return false;
        }

        if (gender.getSelectedToggle() == null) {
            showAlert("Gender is required.");
            return false;
        }

        if (Objects.equals(selection.getText(), "Clubs")){
            showAlert("selecting a club/s is a must.");
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