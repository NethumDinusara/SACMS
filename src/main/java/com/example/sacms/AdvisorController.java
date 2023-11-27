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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TextField advisorNameInput;

    private Club selectedClubRecord;


    private final Database database = new Database();
    private String username;


    @FXML
    private void initialize() {
        // Run this later to ensure all FXML components are injected
        Platform.runLater(() -> {
            // Initialize the TableView columns
            clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
            clubDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
            advisorNameColumn.setCellValueFactory(new PropertyValueFactory<>("advisorName"));

            // Load and set data to the TableView
            loadClubData();
        });
    }


    private void loadClubData() {
        // Retrieve club data from the database
        List<Club> clubs = database.getClubDataForAdvisor();  // You need to implement this method in your Database class

        // Create an ObservableList from the retrieved data
        ObservableList<Club> clubList = FXCollections.observableArrayList(clubs);

        // Set the items of the TableView
        clubTable.setItems(clubList);
    }


    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }



    public void createClub(ActionEvent actionEvent) {
    }

    public void updateClub(ActionEvent actionEvent) {
    }

    public void removeClub(ActionEvent actionEvent) {
    }
}
