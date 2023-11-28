package com.example.sacms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AdvisorController {

    @FXML
    private TableView<Club> tableView;

    //Columns
    @FXML
    private TableColumn<Club, String> clubNameColumn;

    @FXML
    private TableColumn<Club, String> clubDescriptionColumn;

    @FXML
    private TableColumn<Club, String> advisorNameColumn;

    @FXML
    private TableColumn<Club, String> presidentNameColumn;


    //Text input
    @FXML
    private TextField clubNameInput;

    @FXML
    private TextField clubDescriptionInput;

    @FXML
    private TextField advisorNameInput;

    @FXML
    private TextField presidentNameInput;


    @FXML
    private AnchorPane advisorANC;

    private Club selectedClubRecord;

    @FXML
    private Button btnManageClubs;

    @FXML
    private Button btnAttendance;

    @FXML
    private Button btnSearch;

    @FXML
    private SplitPane manageClub;

    @FXML
    private AnchorPane clubAttendance;

    @FXML
    private AnchorPane attendanceTracking;

    @FXML
    private Label advisorName;

    @FXML
    private TableView<Event> eventsTable;

    @FXML
    private TableColumn<Event, Integer> eventIdColumn;

    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, String> clubNameColumn1;

    @FXML
    private TextField eventIdTextField;


    private final Database database = new Database();
    private String username;


    public void initialize() throws SQLException {
        // Set up columns
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        clubNameColumn1.setCellValueFactory(new PropertyValueFactory<>("clubName"));

        // Replace this with actual logic to get the username

        Advisor advisor = database.getAdvisorData(username);
        if(advisor != null){
            setAdvisorData(advisor);
            loadEventsData();
        }
    }


    public void loadEventsData() throws SQLException {
        List<Event> events = database.getAdvisorEvents(username);
        ObservableList<Event> eventData = FXCollections.observableArrayList(events);
        eventsTable.setItems(eventData);

    }


    public void switchingPages(ActionEvent event) throws SQLException {

        if(event.getSource() == btnManageClubs ){
            manageClub.setVisible(true);
            clubAttendance.setVisible(false);
            attendanceTracking.setVisible(false);



        }else if(event.getSource() == btnAttendance){
            manageClub.setVisible(false);
            clubAttendance.setVisible(true);
            attendanceTracking.setVisible(false);

            loadEventsData();


        } else if (event.getSource() == btnSearch) {

            // Set the event id to a text field
            int eventID = Integer.parseInt(eventIdTextField.getText());
            // Import event id list from Database class
            List<Integer> IdList = database.getAdvisorEventIDs(username);

            boolean found = false;

            // Go through the event id list and check the event id is valid or not
            for (Integer evenIDinList : IdList) {
                if (evenIDinList.equals(eventID)) {
                    manageClub.setVisible(false);
                    clubAttendance.setVisible(false);
                    attendanceTracking.setVisible(true);
                    found = true;
                    break;
                }
            }

            // If Event id is not in the list it will print this error
            if (!found) {
                showAlert("Please Enter An Event In The Table.");
            }

            // Clear text field after an execution
            eventIdTextField.setText("");
        }

    }

    public void setUsername(String username){
        this.username=username;
    }

    public void setAdvisorData(Advisor advisor){
        advisorName.setText(advisor.getFirstName()+" "+advisor.getLastName());
    }


    // Get the event details from the table and pass to the table

    @FXML
    public void goBack(ActionEvent actionEvent)throws Exception {
        Stage MainStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainSACMS.fxml")));
        MainStage.setScene(new Scene(root));
        MainStage.setTitle("SACMS");
    }

    public void createClub(ActionEvent actionEvent) {
    }

    public void initialize(MouseEvent mouseEvent) {
    }

    public void updateClub(ActionEvent actionEvent) {
    }

    public void removeClub(ActionEvent actionEvent) {
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
