package com.example.sacms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class MemberLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private final Database database = new Database();

    @FXML
    public void MemberMain(ActionEvent actionEvent) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both username and password");
            return;
        }

        if (database.validateUserLogin(username,password, "member")) {
            Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Member.fxml")));
            MainStage.setScene(new Scene(root));
            MainStage.setTitle("Member");
        } else {
            showAlert("Invalid username or password");
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) throws Exception {
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
