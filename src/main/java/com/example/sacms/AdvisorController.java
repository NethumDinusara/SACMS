package com.example.sacms;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AdvisorController {

    //Table
    @FXML
    private TableColumn<Club, String> advisorNameColumn;

    @FXML
    private TableColumn<Club, String> clubDescriptionColumn;

    @FXML
    private TableColumn<Club, String> clubNameColumn;

    @FXML
    private TableView<Club> clubTable;

    //Text input
    @FXML
    private TextField clubNameInput;

    @FXML
    private TextField clubDescriptionInput;

    @FXML
    private TextField advisorIDInput;

    private Club selectedClubRecord;


    private final Database database = new Database();
    private String username;


    @FXML
    private void initialize() {
        // Initialize the TableView columns
        clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        clubDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
        advisorNameColumn.setCellValueFactory(new PropertyValueFactory<>("advisorName"));

        // Load and set data to the TableView
        loadClubData();
    }

    private void loadClubData() {

        // Retrieve club data from the database
        List<Club> clubs = database.getClubDataForAdvisor();

        // Create an ObservableList from the retrieved data
        ObservableList<Club> clubList = FXCollections.observableArrayList(clubs);

        // Clear existing items before setting the new ones
        clubTable.getItems().clear();

        // Set the items of the TableView
        clubTable.setItems(clubList);

    }

    @FXML
    private void onRowClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            Club clickedClub = clubTable.getSelectionModel().getSelectedItem();
            if (clickedClub != null) {
                // Update the selectedClubRecord when a row is clicked
                selectedClubRecord = clickedClub;
                // Populate the input fields with selected club's data
                populateInputFields(selectedClubRecord);
            }
        }
    }
    // Helper method to populate input fields with club data
    private void populateInputFields(Club club) {
        if (club != null) {
            clubNameInput.setText(club.getClubName());
            clubDescriptionInput.setText(club.getClubDescription());
            advisorIDInput.setText(club.getAdvisorName()); // Assuming AdvisorName is used as AdvisorID
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

    @FXML
    public void createClub(ActionEvent actionEvent) throws SQLException {
        // Get data from input fields
        String clubName = clubNameInput.getText();
        String clubDescription = clubDescriptionInput.getText();
        String advisorID = advisorIDInput.getText();

        // Validate input
        if (clubName.isEmpty() || clubDescription.isEmpty() || advisorID.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }
        if (!database.isAdvisorIdValid(advisorID)) {
            showAlert("Advisor does not exist. Please enter a valid advisor ID.");
            return;
        }

        // Create an instance of ManageClub
        Club.ManageClub newClub = new Club.ManageClub(clubName, clubDescription,"",advisorID);

        // Add the club to the database
        if (database.createClub(newClub)) {
            showAlert("Club created successfully.");
            clearInputFields();
            // Load data after a successful creation if needed
            loadClubData();
        } else {
            showAlert("Error creating club. Please try again.");
        }
    }


    // Helper method to show an alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Club Management");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void updateClub(ActionEvent actionEvent) {
        if (selectedClubRecord == null) {
            // No club selected, show an error message or handle accordingly
            System.out.println("Please select a club to update.");
            return;
        }

        // Get updated values from input fields
        String updatedClubName = clubNameInput.getText();
        String updatedClubDescription = clubDescriptionInput.getText();
        String updatedAdvisorName = advisorIDInput.getText();

        // Validation (You might want to add more sophisticated validation)
        if (updatedClubName.isEmpty() || updatedClubDescription.isEmpty() || updatedAdvisorName.isEmpty()) {
            // Show an error message or handle validation failure
            System.out.println("Please fill in all fields.");
            return;
        }

        // Update the selected club's properties
        selectedClubRecord.setClubName(updatedClubName);
        selectedClubRecord.setClubDescription(updatedClubDescription);
        selectedClubRecord.setAdvisorName(updatedAdvisorName);

        // Update the club in the database
        boolean success = database.updateClub(selectedClubRecord);

        if (success) {
            // Refresh the club data in the TableView
            loadClubData();
            showAlert("Club updated successfully.");
        } else {
            // Show an error message or handle the failure
            showAlert("Error updating club. Please try again.");
        }

        // Clear input fields and reset selectedClubRecord
        clearInputFields();
        selectedClubRecord = null;
    }


    @FXML
    public void removeClub(ActionEvent actionEvent) {
        if (selectedClubRecord == null) {
            // No club selected, show an error message or handle accordingly
            System.out.println("Please select a club to remove.");
            return;
        }

        // Remove the selected club from the database
        boolean success = database.deleteClub(selectedClubRecord.getClubName());

        if (success) {
            // Refresh the club data in the TableView
            loadClubData();
        } else {
            // Show an error message or handle the failure
            System.out.println("Error removing club.");
        }

        // Clear input fields and reset selectedClubRecord
        clearInputFields();
        selectedClubRecord = null;
    }


    private void clearInputFields() {
        clubNameInput.clear();
        clubDescriptionInput.clear();
        advisorIDInput.clear();
    }
}
