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
public class AdvisorLoginController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    private final Database database = new Database();
    @FXML
    public void AdvisorMain(ActionEvent actionEvent) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both username and password");
            return;
        }
        if (database.validateUserLogin(username, password, "advisor")) {

            // Get the member data
            Advisor  advisor = database.getAdvisorData(username);

            if (advisor != null){

                // load the Adviosr.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Advisor.fxml"));
                Parent root = loader.load();
                // Get the controller instance
                AdvisorController advisorController =loader.getController();


                // Set the username and advisor data in advisorcontroller

                advisorController.setUsername(username);
                advisorController.setAdvisorData(advisor);



                // Set the scene
                Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                MainStage.setScene(new Scene(root));
                MainStage.setTitle("Advisor");
            } else {
                showAlert("Failed to retrieve advisor data");
            }

        } else {
            showAlert("Invalid username or password");
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void goRegister(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdvisorRegistration.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("Advisor Registration");
    }

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }
}